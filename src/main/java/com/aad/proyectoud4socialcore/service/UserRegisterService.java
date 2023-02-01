package com.aad.proyectoud4socialcore.service;

import com.aad.proyectoud4socialcore.exception.UserAlreadyExistsException;
import com.aad.proyectoud4socialcore.model.dto.UserDTO;
import com.aad.proyectoud4socialcore.model.entity.Role;
import com.aad.proyectoud4socialcore.model.entity.SocialUser;
import com.aad.proyectoud4socialcore.model.repository.RoleRepository;
import com.aad.proyectoud4socialcore.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
@Transactional
public class UserRegisterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public SocialUser registerNewUserAccount(UserDTO userDTO) throws UserAlreadyExistsException {

        if(userRepository.findSocialUserByEmail(userDTO.getEmail()) != null ) {

            throw new UserAlreadyExistsException("El usuario ya existe");

        }

        SocialUser user = new SocialUser();
        ArrayList<Role> roles = new ArrayList<>();

        // TODO: inlcuír dentro de un enum con los posibles roles de usuario
        roles.add(roleRepository.findByName("ROLE_USER"));

        user.setEmail(userDTO.getEmail());
        user.setFullName(userDTO.getFullName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles(roles);

        return userRepository.save(user);
    }

}
