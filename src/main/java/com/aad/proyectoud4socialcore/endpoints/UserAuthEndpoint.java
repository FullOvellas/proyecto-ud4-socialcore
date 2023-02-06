package com.aad.proyectoud4socialcore.endpoints;

import com.aad.proyectoud4socialcore.model.entity.SocialUser;
import com.aad.proyectoud4socialcore.model.repository.UserRepository;
import com.aad.proyectoud4socialcore.service.UserAuthService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;
import dev.hilla.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.security.RolesAllowed;

@Endpoint
@AnonymousAllowed
@Nonnull
public class UserAuthEndpoint {

    @Autowired
    private UserAuthService userAuthService;


    @AnonymousAllowed
    public String getUserName() {
        return userAuthService.getContextAuthentication().getName();
    }

    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public SocialUser getUser() {
        return userAuthService.getContextUser();
    }

    @AnonymousAllowed
    public boolean isAnonymous() {
        return userAuthService.isAnonymous();
    }

}
