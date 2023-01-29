package com.aad.proyectoud4socialcore.controller;

import com.aad.proyectoud4socialcore.model.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/api/user")
    public ResponseEntity<?> getUser(@RequestParam(required = false) Long id) {

        if (id == null)
            return ResponseEntity.ok().body(userRepository.findAll());

        return ResponseEntity.ok().body(userRepository.findById(id));

    }

}
