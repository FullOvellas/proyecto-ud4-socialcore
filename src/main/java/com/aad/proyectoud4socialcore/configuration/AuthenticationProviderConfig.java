package com.aad.proyectoud4socialcore.configuration;

import com.aad.proyectoud4socialcore.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@Configuration
public class AuthenticationProviderConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserLoginService userLoginService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // Se establece el servicio custom de login de usuario en vez del por defecto de spring
        auth.userDetailsService(userLoginService);

    }

}
