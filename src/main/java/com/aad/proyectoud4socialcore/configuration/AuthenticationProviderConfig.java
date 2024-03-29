package com.aad.proyectoud4socialcore.configuration;

import com.aad.proyectoud4socialcore.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthenticationProviderConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserLoginService userLoginService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // Se establece el servicio custom de login de usuario en vez del por defecto de spring
        auth.userDetailsService(userLoginService).passwordEncoder(encoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.GET,
                        "/index*", "/static/**", "/*.js", "/*.json", "/*.ico")
                .permitAll()
                .and()
                .formLogin().loginPage("/")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true").permitAll();
        /*
        http.csrf().disable().authorizeRequests()
                .antMatchers(
                        HttpMethod.GET,
                        "/index*", "/static/**", "/*.js", "/*.json", "/*.ico")
                .permitAll()
                .and()
                .formLogin().loginPage("/")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/",true)
                .failureUrl("/index.html/login?error=true")
                .and().authorizeRequests().antMatchers("/").authenticated();


         */

    }

    @Bean
    public RoleHierarchy roleHierarchy() {

        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();

        String hierarchy = "ROLE_ADMIN > ROLE_USER";

        roleHierarchy.setHierarchy(hierarchy);

        return roleHierarchy;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
