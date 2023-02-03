package com.aad.proyectoud4socialcore.endpoints;

import com.aad.proyectoud4socialcore.exception.UserAlreadyExistsException;
import com.aad.proyectoud4socialcore.model.dto.UserDTO;
import com.aad.proyectoud4socialcore.model.entity.SocialUser;
import com.aad.proyectoud4socialcore.service.UserRegisterService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;

@Endpoint
@AnonymousAllowed
public class UserRegisterEndpoint {

    private final UserRegisterService userService;

    public UserRegisterEndpoint(UserRegisterService userService) {
        this.userService = userService;
    }

    public void registerUser(UserDTO userDTO ) {

        try {

            SocialUser user = userService.registerNewUserAccount(userDTO);

            // TODO: enviar al usuario credenciales de sesión

        } catch (UserAlreadyExistsException ex )  {

            // TODO: enviar error dentro de la vista de login

        }

    }

}
