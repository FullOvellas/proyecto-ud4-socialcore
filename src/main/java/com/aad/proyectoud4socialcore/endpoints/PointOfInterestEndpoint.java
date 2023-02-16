package com.aad.proyectoud4socialcore.endpoints;

import com.aad.proyectoud4socialcore.exception.PointNotFoundException;
import com.aad.proyectoud4socialcore.model.entity.Comment;
import com.aad.proyectoud4socialcore.model.entity.PointOfInterest;
import com.aad.proyectoud4socialcore.model.entity.SocialUser;
import com.aad.proyectoud4socialcore.model.enums.SocialPlaceType;
import com.aad.proyectoud4socialcore.model.repository.CommentRepository;
import com.aad.proyectoud4socialcore.model.repository.PointOfInterestRepository;
import com.aad.proyectoud4socialcore.service.PointOfInterestService;
import com.aad.proyectoud4socialcore.service.UserAuthService;
import com.google.maps.model.PlaceType;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;
import org.aspectj.lang.reflect.PointcutBasedPerClause;

import java.util.ArrayList;
import java.util.List;

@Endpoint
@AnonymousAllowed
public class PointOfInterestEndpoint {

    private final PointOfInterestRepository repository;
    private final PointOfInterestService service;
    private final CommentRepository commentRepository;
    private final UserAuthService userAuthService;

    public PointOfInterestEndpoint(PointOfInterestRepository repository, UserAuthService userAuthService, CommentRepository commentRepository, PointOfInterestService service) {
        this.repository = repository;
        this.userAuthService = userAuthService;
        this.commentRepository = commentRepository;
        this.service = service;
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

    public Comment[] getCommentsFromPoint(PointOfInterest poi) {

        Comment[] comments = commentRepository.findCommentsByPointOfInterest(poi).toArray(Comment[]::new);

        return comments;
    }

    public PointOfInterest[] findClosePointsOfInterest(SocialUser[] users, String placeTypeString) {

        SocialPlaceType placeType = SocialPlaceType.SCHOOL;

        System.out.println(placeTypeString);

        return service.findClosePoiToUsers(users, placeType);
    }

    public List<PointOfInterest> findAll() {
        return repository.findAll();
    }

    public PointOfInterest add(PointOfInterest pointOfInterest) {
        return repository.save(pointOfInterest);
    }

}
