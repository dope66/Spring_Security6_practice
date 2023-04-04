package com.example.Spring_secuirty_practice.Oauth.controller;

import com.example.Spring_secuirty_practice.Oauth.domain.User;
import com.example.Spring_secuirty_practice.Oauth.domain.dto.UserDTO;
import com.example.Spring_secuirty_practice.Oauth.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/join")
    public String showJoin() {
        return "member/join";
    }

    @PostMapping("/joinProc")
    public String doJoin(UserDTO userDTO,Model model) throws Exception {
            System.out.println("여기로 들어오긴하는지");
            User newUser = memberService.doJoin(userDTO);
            return "redirect:/";

    }

    @GetMapping("/login")
    public String showLogin(){

        return "member/login";
    }



}
