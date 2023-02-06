package com.aad.proyectoud4socialcore.endpoints;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;
import dev.hilla.Nonnull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Endpoint
@AnonymousAllowed
@Nonnull
public class UserAuthEndpoint {

    private Authentication getContextAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @AnonymousAllowed
    public String getUserName() {
        Authentication auth = getContextAuthentication();

        return auth.getName();
    }

    @AnonymousAllowed
    public boolean isAnonymous() {
        Authentication auth = getContextAuthentication();

        return auth instanceof AnonymousAuthenticationToken;
    }

}
