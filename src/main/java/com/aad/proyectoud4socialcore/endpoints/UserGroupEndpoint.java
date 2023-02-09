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
import java.util.Objects;

/**
 * Endpoint de operaciones con grupos de usuarios. Solo se permite acceso a los usuarios ADMIN o usuarios autentificados
 */
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

    /**
     * Comprueba si el usuario del contexto es el credor del grupo
     * @param group el grupo para comprobar
     * @return true si es el creador
     */
    public boolean isCreator(UserGroup group) {

        SocialUser contextUser = userAuthService.getContextUser();

        return group.getCreator().equals(contextUser);
    }


    /**
     * Obtiene un grupo de usuarios a partir del Id
     * @param id el id del grupo
     * @return el grupo
     * @throws NullPointerException si no existe
     * @throws ForbidenAccessException si el usuario no pertenece al grupo del cual pide los datos
     */
    public UserGroup getGroupById(Long id) throws NullPointerException, ForbidenAccessException {

        UserGroup group = userGroupRepository.findUserGroupById(id);
        SocialUser user = userAuthService.getContextUser();

        if(group == null) {
            throw new NullPointerException();
        }

        if(!group.getParticipants().contains(user)) {
            throw new ForbidenAccessException("User can't access this group");
        }

        return group;
    }

    /**
     * Obtiene todos los grupos del usuario del contexto
     * @return los grupos
     */
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

    /**
     * Añade un usuario a un grupo a partir del email
     * @param email el email del usuario para añadir
     * @param group el grupo donde añadir el usuario
     * @throws UserNotFoundException si el email no pertenece a ningún usuario
     * @throws ForbidenAccessException si el usuario no es el creador del grupo
     */
    public void addUserToGroup(String email, UserGroup group) throws UserNotFoundException, ForbidenAccessException {

        SocialUser user = userRepository.findSocialUserByEmail(email);
        SocialUser contextUser = userAuthService.getContextUser();

        if(user == null) {

            throw new UserNotFoundException("Usuario no encontrado");

        }

        if(!Objects.equals(group.getCreator().getId(), contextUser.getId())) {

            throw new ForbidenAccessException("User isn't the creator of this group");

        }

        userGroupService.addUserToGroup(user, group);

    }

    /**
     * Borra un usuario de un grupo
     * @param group el grupo donde borrar el usuario
     * @param user el usuario a borrar del grupo
     * @throws ForbidenAccessException si el usuario del contexto no es el creador del grupo
     */
    public void removeUserFromGroup(UserGroup group, SocialUser user) throws ForbidenAccessException {

        SocialUser contextUser = userAuthService.getContextUser();

        if(!Objects.equals(group.getCreator().getId(), contextUser.getId())) {

            throw new ForbidenAccessException("User isn't the creator of this group");

        }

        userGroupService.removeUserFromGroup(group, user);

    }

    /**
     * Borra un grupo de usuarios
     * @param group el grupo a borrar
     * @throws ForbidenAccessException si el usuario que intenta borrar el grupo no es el creador del grupo
     */
    public void deleteGroup(UserGroup group ) throws ForbidenAccessException{

        SocialUser contextUser = userAuthService.getContextUser();

        if(!group.getCreator().equals(contextUser)) {
            return;
        }

        userGroupRepository.delete(group);

    }

    /**
     * Permite a un usuario salir de un grupo
     * @param group el grupo de donde salir
     * @return true si se pudo salir del grupo
     */
    public boolean exitGroup(UserGroup group) {

        SocialUser user = userAuthService.getContextUser();

        if(user == null) {
            return false;
        }

        userGroupService.removeUserFromGroup(group, user);

        return true;
    }

}
