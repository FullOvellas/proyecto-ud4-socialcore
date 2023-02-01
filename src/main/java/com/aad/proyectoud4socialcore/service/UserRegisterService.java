package com.aad.proyectoud4socialcore.service;

import com.aad.proyectoud4socialcore.exception.UserAlreadyExistsException;
import com.aad.proyectoud4socialcore.model.dto.UserDTO;
import com.aad.proyectoud4socialcore.model.entity.SocialUser;
import com.aad.proyectoud4socialcore.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
@Transactional
public class UserRegisterService {

    @Autowired
    public UserRepository userRepository;

    public SocialUser registerNewUserAccount(UserDTO userDTO) throws UserAlreadyExistsException {

        if(userRepository.findSocialUserByEmail(userDTO.getEmail()) != null ) {

            throw new UserAlreadyExistsException("El usuario ya existe");

        }

        SocialUser user = new SocialUser();
        ArrayList<String> roles = new ArrayList<>();

        // TODO: inlcuír dentro de un enum con los posibles roles de usuario
        roles.add("NORMAL_USER");

        user.setEmail(userDTO.getEmail());
        user.setFullName(userDTO.getFullName());
        user.setPassword(userDTO.getPassword());
        user.setRoles(roles);

        return userRepository.save(user);
    }

}
