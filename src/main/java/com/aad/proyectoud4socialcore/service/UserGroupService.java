package com.aad.proyectoud4socialcore.service;

import com.aad.proyectoud4socialcore.model.entity.SocialUser;
import com.aad.proyectoud4socialcore.model.entity.UserGroup;
import com.aad.proyectoud4socialcore.model.repository.UserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGroupService {

    @Autowired
    private UserGroupRepository userGroupRepository;

    public void createUserGroup(SocialUser creator, String name ) {

        UserGroup userGroup = new UserGroup();

        userGroup.setCreator(creator);
        userGroup.setName(name);
        userGroup.getParticipants().add(creator);

        userGroupRepository.save(userGroup);
    }

}
