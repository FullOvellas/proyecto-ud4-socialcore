package com.aad.proyectoud4socialcore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReactController {

    @RequestMapping(value = {"/", "/login", "/register"})
    public String getReactRoot() {
        return "/index.html";
    }

}
