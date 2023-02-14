package com.aad.proyectoud4socialcore.endpoints;

import com.aad.proyectoud4socialcore.exception.PointNotFoundException;
import com.aad.proyectoud4socialcore.model.entity.PointOfInterest;
import com.aad.proyectoud4socialcore.model.entity.SocialUser;
import com.aad.proyectoud4socialcore.model.repository.PointOfInterestRepository;
import com.aad.proyectoud4socialcore.service.UserAuthService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;

import java.util.List;

@Endpoint
@AnonymousAllowed
public class PointOfInterestEndpoint {

    private final PointOfInterestRepository repository;
    private final UserAuthService userAuthService;

    public PointOfInterestEndpoint(PointOfInterestRepository repository, UserAuthService userAuthService) {
        this.repository = repository;
        this.userAuthService = userAuthService;
    }

    /**
     * Devuelve un punto de interés a partir de su Id
     * @param id el id del punto
     * @return el punto de interés
     * @throws PointNotFoundException si no se encuentra el punto
     */
    public PointOfInterest getPoiFromId(long id) throws PointNotFoundException {

        PointOfInterest point = repository.findPointOfInterestById(id);

        if(point == null) {
            throw new PointNotFoundException("Point not found");
        }

        return point;
    }

    public List<PointOfInterest> findAll() {
        return repository.findAll();
    }

    public PointOfInterest add(PointOfInterest pointOfInterest) {
        return repository.save(pointOfInterest);
    }

}
