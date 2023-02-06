package com.aad.proyectoud4socialcore.endpoints;

import com.aad.proyectoud4socialcore.exception.GroupAlreadyExsistsException;
import com.aad.proyectoud4socialcore.exception.UserAlreadyExistsException;
import com.aad.proyectoud4socialcore.model.entity.SocialUser;
import com.aad.proyectoud4socialcore.model.entity.UserGroup;
import com.aad.proyectoud4socialcore.model.repository.UserGroupRepository;
import com.aad.proyectoud4socialcore.model.repository.UserRepository;
import com.aad.proyectoud4socialcore.service.UserGroupService;
import dev.hilla.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;

@Endpoint
@RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
public class UserGroupEndpoint {

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;

    public UserGroup[] getUserGroups() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SocialUser user = userRepository.findSocialUserByEmail(authentication.getName());

        if(user == null) {
            return new UserGroup[0];
        }

        List<UserGroup> groups = userGroupRepository.findUserGroupByParticipantsContaining(user);

        if(groups == null) {
            return new UserGroup[0];
        }

        return groups.toArray(UserGroup[]::new);
    }

    /**
     * Crea un nuevo grupo cuyo creador es el usuario de la sesión que llama a este método
     * @param name nombre del grupo a crear
     * @return true si se ha creado el grupo y false si no se pudo crear
     */
    public boolean createUserGroup(String name) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SocialUser user = userRepository.findSocialUserByEmail(authentication.getName());

        if(user == null) {
            return false;
        }

        try {

            userGroupService.createUserGroup(user, name);
            return true;

        } catch (GroupAlreadyExsistsException ex ) {

        }

        return false;
    }

}
