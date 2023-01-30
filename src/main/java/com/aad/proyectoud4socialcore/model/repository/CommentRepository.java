package com.aad.proyectoud4socialcore.model.repository;

import com.aad.proyectoud4socialcore.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
