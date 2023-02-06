package com.aad.proyectoud4socialcore.endpoints;

import com.aad.proyectoud4socialcore.exception.ForbidenAccessException;
import com.aad.proyectoud4socialcore.exception.GroupAlreadyExsistsException;
import com.aad.proyectoud4socialcore.exception.UserNotFoundException;
import com.aad.proyectoud4socialcore.model.entity.SocialUser;
import com.aad.proyectoud4socialcore.model.entity.UserGroup;
import com.aad.proyectoud4socialcore.model.repository.UserGroupRepository;
import com.aad.proyectoud4socialcore.model.repository.UserRepository;
import com.aad.proyectoud4socialcore.service.UserAuthService;
import com.aad.proyectoud4socialcore.service.UserGroupService;
import dev.hilla.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Endpoint
@RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
public class UserGroupEndpoint {

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;

    public UserGroup getGroupById(Long id) throws NullPointerException, UserNotFoundException, ForbidenAccessException {

        UserGroup group = userGroupRepository.findUserGroupById(id);
        SocialUser user = userAuthService.getContextUser();

        if(user == null ) {
            throw new UserNotFoundException("Usuario no encontrado");
        }

        if(group == null) {
            throw new NullPointerException();
        }

        if(!group.getParticipants().contains(user)) {
            throw new ForbidenAccessException();
        }

        return group;
    }

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
    public boolean createUserGroup(String name) throws GroupAlreadyExsistsException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SocialUser user = userRepository.findSocialUserByEmail(authentication.getName());

        if(user == null) {
            return false;
        }

        userGroupService.createUserGroup(user, name);
        return true;

    }

    public void addUserToGroup(String email, UserGroup group) throws UserNotFoundException {

        SocialUser user = userRepository.findSocialUserByEmail(email);

        if(user == null) {

            throw new UserNotFoundException("Usuario no encontrado");

        }

        userGroupService.addUserToGroup(user, group);
        userGroupRepository.save(group);

    }

    public void removeUserFromGroup(UserGroup group, SocialUser user) {

        if(group == null || user == null) {
            return;
        }

        userGroupService.removeUserFromGroup(UserGroup group, SocialUser user);

    }

}
