package com.saosao.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class WebController {

    @RequestMapping("/login")
    public String TestString() {
        return "login";
    }
}
