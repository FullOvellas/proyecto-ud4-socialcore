package com.aad.proyectoud4socialcore.controller;

import com.aad.proyectoud4socialcore.exception.UserAlreadyExistsException;
import com.aad.proyectoud4socialcore.model.dto.UserDTO;
import com.aad.proyectoud4socialcore.model.entity.SocialUser;
import com.aad.proyectoud4socialcore.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    @Autowired
    private UserRegisterService registerService;

    @PostMapping("/register")
    public String registerUser(@RequestParam(name = "email") String email, @RequestParam(name = "fullName") String fullName, @RequestParam(name = "password") String password ) {

        try {

            SocialUser user = registerService.registerNewUserAccount(new UserDTO(fullName, email, password));

            // Devolver usuario al cliente

        } catch (UserAlreadyExistsException ex ) {

            // Devolver error al cliente
            return "redirect:register?error=true";

        }

        return "redirect:/homepage";
    }

}
