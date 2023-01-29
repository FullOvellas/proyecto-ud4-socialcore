package com.aad.proyectoud4socialcore;

import com.aad.proyectoud4socialcore.model.entity.User;
import com.aad.proyectoud4socialcore.model.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class Initializer implements CommandLineRunner {

    UserRepository userRepository;

    public Initializer(UserRepository userRepository) {

        this.userRepository = userRepository;

    }

    @Override
    public void run(String... args) throws Exception {

        Stream.of("lucascabaleiro", "margb7", "FullOvellas")
                .forEach(name -> userRepository.save(new User(name)));

        userRepository.findAll().forEach(System.out::println);

    }
}
