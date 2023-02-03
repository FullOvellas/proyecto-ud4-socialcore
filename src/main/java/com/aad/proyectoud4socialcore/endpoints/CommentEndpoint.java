package com.aad.proyectoud4socialcore.endpoints;

import com.aad.proyectoud4socialcore.model.entity.Comment;
import com.aad.proyectoud4socialcore.model.repository.CommentRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;

import java.util.List;

@Endpoint
@AnonymousAllowed
public class CommentEndpoint {

    private final CommentRepository repository;

    public CommentEndpoint(CommentRepository repository) {
        this.repository = repository;
    }

    public List<Comment> findAll() {
        return repository.findAll();
    }

    public Comment add(Comment comment) {
        return repository.save(comment);
    }

}