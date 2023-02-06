package com.aad.proyectoud4socialcore;

import com.aad.proyectoud4socialcore.service.UserRegisterService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Assert;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
classes = Application.class)
@RunWith(SpringRunner.class)    // Sustituir por mokito
@DisplayName("Tests de credenciales")
public class CredentialsTest {

    @Autowired
    private UserRegisterService userRegisterService;

    @Test
    @DisplayName("Test - Contraseña vacía")
    public void testEmptyPass() {
        Assert.assertFalse(userRegisterService.isPasswordValid(null));
    }

    @Test
    @DisplayName("Test - Contraseña < 5")
    public void testShortPass() {
        Assert.assertFalse(userRegisterService.isPasswordValid("1234"));
    }

    @Test
    @DisplayName("Test - Contraseña espacios")
    public void testSpacePass() {
        Assert.assertFalse(userRegisterService.isPasswordValid("1 23 4"));
    }

    @Test
    @DisplayName("Test - Contraseña válida")
    public void testValidPassword() {
        Assert.assertTrue(userRegisterService.isPasswordValid("contraseña1234"));
    }

}
