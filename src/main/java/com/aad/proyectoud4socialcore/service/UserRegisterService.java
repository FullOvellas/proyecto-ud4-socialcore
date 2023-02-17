package com.aad.proyectoud4socialcore.service;

import com.aad.proyectoud4socialcore.exception.UserAlreadyExistsException;
import com.aad.proyectoud4socialcore.model.dto.UserDTO;
import com.aad.proyectoud4socialcore.model.entity.Residence;
import com.aad.proyectoud4socialcore.model.entity.Role;
import com.aad.proyectoud4socialcore.model.entity.SocialUser;
import com.aad.proyectoud4socialcore.model.repository.RoleRepository;
import com.aad.proyectoud4socialcore.model.repository.UserRepository;
import com.google.maps.model.LatLng;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserRegisterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public SocialUser registerNewUserAccount(UserDTO userDTO) throws UserAlreadyExistsException, InvalidCredentialsException {

        if(userRepository.findSocialUserByEmail(userDTO.getEmail()) != null ) {

            throw new UserAlreadyExistsException("El usuario ya existe");

        }

        if(!isEmailValid(userDTO.getEmail()) || !isPasswordValid(userDTO.getPassword())) {
            throw new InvalidCredentialsException("Credenciales incorrectas");
        }

        SocialUser user = new SocialUser();
        ArrayList<Role> roles = new ArrayList<>();
        Residence userResidence = new Residence();

        // TODO: inlcuír dentro de un enum con los posibles roles de usuario
        roles.add(roleRepository.findByName("ROLE_USER"));

        user.setEmail(userDTO.getEmail());
        user.setFullName(userDTO.getFullName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles(roles);

        userResidence.setCoordinates(new LatLng(userDTO.getLat(), userDTO.getLng()));

        user.setResidence(userResidence);

        return userRepository.save(user);
    }

    public boolean isEmailValid(String email ) {

        Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        if(email == null) {
            return false;
        }

        return emailPattern.matcher(email).find();
    }

    public boolean isPasswordValid(String password ) {

        if(password == null || password.length() < 5 ) {
            return false;
        }

        if(password.contains(" ")) {
            return false;
        }

        // Más consideraciones para una contraseña segura

        return true;
    }

}
