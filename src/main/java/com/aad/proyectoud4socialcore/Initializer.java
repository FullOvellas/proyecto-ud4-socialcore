package com.aad.proyectoud4socialcore;

import com.aad.proyectoud4socialcore.model.entity.Role;
import com.aad.proyectoud4socialcore.model.entity.SocialUser;
import com.aad.proyectoud4socialcore.model.repository.RoleRepository;
import com.aad.proyectoud4socialcore.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
public class Initializer implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    public Initializer(UserRepository userRepository) {

        this.userRepository = userRepository;

    }

    @Override
    public void run(String... args) throws Exception {

        Stream.of("lucascabaleiro", "margb7", "FullOvellas")
                .forEach(name -> {

                    SocialUser user = new SocialUser();
                    List<Role> roles = new ArrayList<>();

                    roles.add(roleRepository.findByName("ROLE_ADMIN"));

                    user.setEmail(name);
                    user.setFullName(name);
                    user.setPassword(encoder.encode("1234"));
                    user.setRoles(roles);

                    userRepository.save(user);
                });

    }
}
