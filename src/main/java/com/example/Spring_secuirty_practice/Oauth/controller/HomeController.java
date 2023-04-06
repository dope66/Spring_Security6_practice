package com.example.Spring_secuirty_practice.Oauth.controller;

import com.example.Spring_secuirty_practice.Oauth.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    @Autowired
    private final MemberService memberService;
    @GetMapping("/")
    public String showMain(Model model){
        model.addAttribute("username",memberService.getUsername());
        return "main";
    }

    @GetMapping("/home")
    public String showHome(){
        return "main";
    }


}
