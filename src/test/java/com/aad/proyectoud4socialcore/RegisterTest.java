package com.aad.proyectoud4socialcore;

import com.aad.proyectoud4socialcore.model.dto.UserDTO;
import com.aad.proyectoud4socialcore.service.UserRegisterService;
import org.apache.http.auth.InvalidCredentialsException;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Application.class)
@RunWith(SpringRunner.class)    // Sustituir por mokito
public class RegisterTest {

    @Autowired
    private UserRegisterService userRegisterService;

    @Test
    @DisplayName("Test - Email no válido")
    public void test1() {

        UserDTO dto = new UserDTO();

        dto.setEmail("random_email");
        dto.setPassword("12345");

        Assert.assertThrows(InvalidCredentialsException.class, () -> {

            userRegisterService.registerNewUserAccount(dto);

        });

    }

    @Test
    @DisplayName("Test - Contraseña no válida")
    public void test2() {

        UserDTO dto = new UserDTO();

        dto.setEmail("email_correcto@gmail.com");
        dto.setFullName("fullName");
        dto.setPassword("12");

        Assert.assertThrows(InvalidCredentialsException.class, () -> {

            userRegisterService.registerNewUserAccount(dto);

        });

    }

    @Test
    @DisplayName("Test - Usuario válido")
    public void test3() {

        UserDTO dto = new UserDTO();

        dto.setEmail("email_correcto@gmail.com");
        dto.setPassword("12345");

        try {

            Assert.assertNotNull(userRegisterService.registerNewUserAccount(dto));

        } catch (Exception ex ) {

        }

    }

}
