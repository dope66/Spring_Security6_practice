package com.example.Spring_secuirty_practice.Oauth.controller;

import com.example.Spring_secuirty_practice.Oauth.domain.User;
import com.example.Spring_secuirty_practice.Oauth.domain.dto.UserDTO;
import com.example.Spring_secuirty_practice.Oauth.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;


    @GetMapping("/join")
    public String showJoin(Model model) {
        model.addAttribute("userDTO",new UserDTO());
        return "member/join";
    }

    @PostMapping("/joinProc")
    public String doJoin(@Valid  UserDTO userDTO, Errors errors, Model model) throws Exception {

        if(errors.hasErrors()){
//            회원 가입 실패시 입력 테이터 값을 유지
            model.addAttribute("userDTO",userDTO);
//            유효성 통과 못한 필드와 메세지를 핸들링
            Map<String, String> validatorResult = memberService.validateHandling(errors);
            for (String key : validatorResult.keySet()){
                model.addAttribute(key,validatorResult.get(key));
                System.out.println(validatorResult);
            }
            return "member/join";
        }
        if(memberService.isDuplicateId(userDTO.getUsername())){
            model.addAttribute("valid_username", "이미 사용중인 아이디입니다.");
            return "member/join";
        }
            User newUser = memberService.doJoin(userDTO);
            return "redirect:/";

    }

    @GetMapping("/login")
    public String showLogin(){

        return "member/login";
    }
    @PostMapping("/login")
    public String doLogin(){
        return "member/login";
    }
    @GetMapping("/logout")
    public String doLogout(){
        return "member/logout";
    }

    @GetMapping("/myPage")
    public String showMyPage(Model model){
        model.addAttribute("username",memberService.getUsername());
        model.addAttribute("email",memberService.getEmaiL());
        System.out.println("email = "+ model.addAttribute("email",memberService.getEmaiL()));
        return "member/myPage";
    }



}
