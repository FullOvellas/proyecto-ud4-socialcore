package com.aad.proyectoud4socialcore.exception;

import dev.hilla.exception.EndpointException;

public class ForbidenAccessException extends EndpointException {

    public ForbidenAccessException(String message) {

        super(message);

    }
}
