package com.aad.proyectoud4socialcore.service;

import com.aad.proyectoud4socialcore.model.entity.Comment;
import com.aad.proyectoud4socialcore.model.entity.PointOfInterest;
import com.aad.proyectoud4socialcore.model.entity.SocialUser;
import com.aad.proyectoud4socialcore.model.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment createComment(SocialUser user, String message, PointOfInterest poi, Float rating) {

        Comment comment = new Comment();

        comment.setPointOfInterest(poi);
        comment.setUser(user);
        comment.setText(message);
        comment.setRating(rating);

        return commentRepository.save(comment);
    }

    public void deleteComment(Comment comment ) {
        commentRepository.delete(comment);
    }

}
