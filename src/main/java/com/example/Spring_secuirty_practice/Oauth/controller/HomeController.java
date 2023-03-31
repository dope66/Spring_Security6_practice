package com.example.Spring_secuirty_practice.Oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String showMain(){
        return "main";
    }

    @GetMapping("/home")
    public String showHome(){
        return "main";
    }


}