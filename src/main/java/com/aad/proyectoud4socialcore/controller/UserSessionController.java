package com.aad.proyectoud4socialcore.controller;

import com.aad.proyectoud4socialcore.exception.UserAlreadyExistsException;
import com.aad.proyectoud4socialcore.model.dto.UserDTO;
import com.aad.proyectoud4socialcore.model.entity.SocialUser;
import com.aad.proyectoud4socialcore.service.UserRegisterService;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserSessionController {

    @Autowired
    private UserRegisterService registerService;

    @PostMapping("/register")
    public String registerUser(@RequestParam(name = "email") String email,
                               @RequestParam(name = "fullName") String fullName,
                               @RequestParam(name = "password") String password,
                               @RequestParam(name = "lat") String lat,
                               @RequestParam(name = "lng") String lng,
                               HttpServletRequest request)
    {

        try {

            double latDouble = Double.parseDouble(lat);
            double lngDouble = Double.parseDouble(lng);

            SocialUser user = registerService.registerNewUserAccount(new UserDTO(fullName, email, password, latDouble, lngDouble));

            // Devolver usuario al cliente
            request.login(email, password);
            return "redirect:/";

        } catch (UserAlreadyExistsException | ServletException | InvalidCredentialsException | NumberFormatException ex ) {

            // Devolver error al cliente
            return "redirect:register?error=true";
        }

    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request ) {

        HttpSession session = request.getSession(false);

        if(session != null) {

            session.invalidate();

        }

        return "redirect:/";
    }

}
