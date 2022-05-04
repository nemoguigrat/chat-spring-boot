package com.chat.reactchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping(value = {"/", "/login", "/register"})
    public String getIndex(){
        return "forward:/index.html";
    }
}
