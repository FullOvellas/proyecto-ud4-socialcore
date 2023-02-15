package com.aad.proyectoud4socialcore.model.repository;

import com.aad.proyectoud4socialcore.model.entity.Comment;
import com.aad.proyectoud4socialcore.model.entity.PointOfInterest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByPointOfInterest(PointOfInterest poi);

}
