package com.aad.proyectoud4socialcore.endpoints;

import com.aad.proyectoud4socialcore.model.entity.PointOfInterest;
import com.aad.proyectoud4socialcore.model.entity.User;
import com.aad.proyectoud4socialcore.model.repository.PointOfInterestRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;

import java.util.List;

@Endpoint
@AnonymousAllowed
public class PointOfInterestEndpoint {

    private final PointOfInterestRepository repository;

    public PointOfInterestEndpoint(PointOfInterestRepository repository) {
        this.repository = repository;
    }

    public List<PointOfInterest> findAll() {
        return repository.findAll();
    }

    public PointOfInterest add(PointOfInterest pointOfInterest) {
        return repository.save(pointOfInterest);
    }

}
