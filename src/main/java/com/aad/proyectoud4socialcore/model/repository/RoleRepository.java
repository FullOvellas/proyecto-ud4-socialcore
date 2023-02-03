package com.aad.proyectoud4socialcore.model.repository;

import com.aad.proyectoud4socialcore.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

}
