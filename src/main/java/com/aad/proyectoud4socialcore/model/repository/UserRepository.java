package com.aad.proyectoud4socialcore.model.repository;

import com.aad.proyectoud4socialcore.model.entity.SocialUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SocialUser, Long> {
}
