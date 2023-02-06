package com.aad.proyectoud4socialcore.exception;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.exception.EndpointException;

public class UserNotFoundException extends EndpointException {

    public UserNotFoundException(String message) {

        super(message);

    }

}
