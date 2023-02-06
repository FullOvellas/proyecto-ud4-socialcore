package com.aad.proyectoud4socialcore.model.repository;

import com.aad.proyectoud4socialcore.model.entity.SocialUser;
import com.aad.proyectoud4socialcore.model.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

    List<UserGroup> findUserGroupByParticipantsContaining(SocialUser user);

    UserGroup findUserGroupByNameAndCreator(String name, SocialUser user);

}
