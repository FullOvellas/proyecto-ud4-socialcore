package com.aad.proyectoud4socialcore.endpoints;

import com.aad.proyectoud4socialcore.model.entity.Residence;
import com.aad.proyectoud4socialcore.model.repository.ResidenceRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;

import java.util.List;

@Endpoint
@AnonymousAllowed
public class ResidenceEndpoint {

    private final ResidenceRepository repository;

    public ResidenceEndpoint(ResidenceRepository repository) {
        this.repository = repository;
    }

    public List<Residence> findAll() {
        return repository.findAll();
    }
}
