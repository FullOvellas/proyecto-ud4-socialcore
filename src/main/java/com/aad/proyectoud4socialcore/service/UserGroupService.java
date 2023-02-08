package com.aad.proyectoud4socialcore.service;

import com.aad.proyectoud4socialcore.exception.GroupAlreadyExsistsException;
import com.aad.proyectoud4socialcore.exception.UserNotFoundException;
import com.aad.proyectoud4socialcore.model.entity.SocialUser;
import com.aad.proyectoud4socialcore.model.entity.UserGroup;
import com.aad.proyectoud4socialcore.model.repository.UserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio para gestionar la creación, modificación y borrado de los grupos de usuarios
 */
@Service
public class UserGroupService {

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private UserAuthService userAuthService;

    /**
     * Crea un grupo de usuarios
     * @param creator el creador del grupo que hará de admin
     * @param name el nombre del grupo
     * @throws GroupAlreadyExsistsException en caso de que el usuario creador ya tenga un grupo con el mismo nombre
     */
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

    /**
     * Añade un usuario a un grupo. No permite que un usuario se encuentre varias veces en el mismo grupo
     * @param user el usuario a añadir
     * @param group el grupo donde añadir el usuario
     */
    public void addUserToGroup(SocialUser user, UserGroup group ) {

        if(group.getParticipants().contains(user)) {
            return;
        }

        group.getParticipants().add(user);
        userGroupRepository.save(group);

    }

    /**
     * Método para eliminar a un usuario de un grupo. En caso de que el usuario sea el creador no lo eliminará
     * @param group el grupo del cual eliminar el usuario
     * @param user el usuario a eliminar
     */
    public void removeUserFromGroup(UserGroup group, SocialUser user) {

        if(group.getCreator().equals(user)) {
            return;
        }

        group.getParticipants().remove(user);
        userGroupRepository.save(group);
    }

}
