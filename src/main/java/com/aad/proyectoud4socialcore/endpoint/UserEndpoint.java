package com.aad.proyectoud4socialcore.endpoint;

import com.aad.proyectoud4socialcore.model.entity.User;
import com.aad.proyectoud4socialcore.model.repository.UserRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;

import java.util.List;

@Endpoint
@AnonymousAllowed
public class UserEndpoint {

    private UserRepository repository;

    public UserEndpoint(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User add(User user) {
        return repository.save(user);
    }

}
