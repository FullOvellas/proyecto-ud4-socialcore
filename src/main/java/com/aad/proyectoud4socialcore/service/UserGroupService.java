package com.aad.proyectoud4socialcore.service;

import com.aad.proyectoud4socialcore.exception.GroupAlreadyExsistsException;
import com.aad.proyectoud4socialcore.exception.UserNotFoundException;
import com.aad.proyectoud4socialcore.model.entity.SocialUser;
import com.aad.proyectoud4socialcore.model.entity.UserGroup;
import com.aad.proyectoud4socialcore.model.repository.UserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGroupService {

    @Autowired
    private UserGroupRepository userGroupRepository;

    public void createUserGroup(SocialUser creator, String name ) throws GroupAlreadyExsistsException {

        if(creator == null) {
            return;
        }

        UserGroup group = userGroupRepository.findUserGroupByNameAndCreator(name, creator);

        if(group != null) {
            return;
        }

        UserGroup userGroup = new UserGroup();

        userGroup.setCreator(creator);
        userGroup.setName(name);
        userGroup.getParticipants().add(creator);

        userGroupRepository.save(userGroup);
    }

    public void addUserToGroup(SocialUser user, UserGroup group ) {

        if(group.getParticipants().contains(user)) {
            return;
        }

        group.getParticipants().add(user);

    }

    public void removeUserFromGroup(UserGroup group, SocialUser user) {

        // TODO: seguridad de usuarios

        if(group.getParticipants().contains(user)) {

            group.getParticipants().remove(user);

        }

        userGroupRepository.save(group);
    }

}
