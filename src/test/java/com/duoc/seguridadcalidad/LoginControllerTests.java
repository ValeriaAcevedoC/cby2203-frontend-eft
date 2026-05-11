package com.duoc.seguridadcalidad;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginControllerTests {

    private final LoginController loginController = new LoginController();

    @Test
    void loginShouldReturnLoginView() {
        assertEquals("login", loginController.login());
    }
}
