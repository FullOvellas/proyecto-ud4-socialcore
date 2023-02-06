package com.aad.proyectoud4socialcore.service;

import com.aad.proyectoud4socialcore.model.entity.SocialUser;
import com.aad.proyectoud4socialcore.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService {

    @Autowired
    private UserRepository userRepository;

    public Authentication getContextAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public SocialUser getContextUser() {

        if(isAnonymous()) {
            return null;
        }

        return userRepository.findSocialUserByEmail(getContextAuthentication().getName());
    }

    public boolean isAnonymous() {
        Authentication auth = getContextAuthentication();

        return auth instanceof AnonymousAuthenticationToken;
    }

}
