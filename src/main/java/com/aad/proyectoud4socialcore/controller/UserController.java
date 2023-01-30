package com.aad.proyectoud4socialcore.controller;

import com.aad.proyectoud4socialcore.model.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(@RequestParam(required = false) Long id) {

        if (id == null)
            return ResponseEntity.ok().body(userRepository.findAll());

        return ResponseEntity.ok().body(userRepository.findById(id));

    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {

        userRepository.deleteById(id);
        return ResponseEntity.ok().build();

    }

}
