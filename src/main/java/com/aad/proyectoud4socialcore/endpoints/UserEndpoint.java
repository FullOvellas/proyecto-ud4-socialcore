package com.aad.proyectoud4socialcore.endpoints;

import com.aad.proyectoud4socialcore.model.entity.SocialUser;
import com.aad.proyectoud4socialcore.model.repository.UserRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;

import java.util.List;

@Endpoint
@AnonymousAllowed
public class UserEndpoint {

    private final UserRepository repository;

    public UserEndpoint(UserRepository repository) {
        this.repository = repository;
    }

    public List<SocialUser> findAll() {
        return repository.findAll();
    }

    public SocialUser add(SocialUser user) {
        return repository.save(user);
    }

}
