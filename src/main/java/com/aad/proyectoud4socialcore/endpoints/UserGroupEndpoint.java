package com.aad.proyectoud4socialcore.endpoints;

import com.aad.proyectoud4socialcore.model.entity.SocialUser;
import com.aad.proyectoud4socialcore.model.entity.UserGroup;
import com.aad.proyectoud4socialcore.model.repository.UserGroupRepository;
import com.aad.proyectoud4socialcore.model.repository.UserRepository;
import dev.hilla.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Endpoint
@RolesAllowed("ROLE_USER")
public class UserGroupEndpoint {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;

    public List<UserGroup> getUserGroups() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SocialUser user = userRepository.findSocialUserByEmail(authentication.getName());

        return userGroupRepository.findUserGroupByParticipantsContaining(user);
    }

}
