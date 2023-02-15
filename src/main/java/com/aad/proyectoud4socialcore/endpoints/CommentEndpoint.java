package com.aad.proyectoud4socialcore.endpoints;

import com.aad.proyectoud4socialcore.model.entity.Comment;
import com.aad.proyectoud4socialcore.model.entity.PointOfInterest;
import com.aad.proyectoud4socialcore.model.entity.SocialUser;
import com.aad.proyectoud4socialcore.model.repository.CommentRepository;
import com.aad.proyectoud4socialcore.service.CommentService;
import com.aad.proyectoud4socialcore.service.UserAuthService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Endpoint
@RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
public class CommentEndpoint {

    private final CommentRepository repository;
    private final CommentService commentService;
    private final UserAuthService userAuthService;

    public CommentEndpoint(CommentRepository repository, UserAuthService userAuthService, CommentService commentService) {
        this.repository = repository;
        this.userAuthService = userAuthService;
        this.commentService = commentService;
    }

    public Comment createComment(PointOfInterest poi, String message ) {

        SocialUser user = userAuthService.getContextUser();

        return commentService.createComment(user, message, poi);
    }

    public boolean deleteComment(Comment comment ) {

        SocialUser user = userAuthService.getContextUser();

        if(!comment.getUser().equals(user)) {
            return false;
        }

        commentService.deleteComment(comment);
        return true;
    }

    public List<Comment> findAll() {
        return repository.findAll();
    }

    public Comment add(Comment comment) {
        return repository.save(comment);
    }

}
