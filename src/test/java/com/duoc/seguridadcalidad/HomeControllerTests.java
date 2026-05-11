package com.duoc.seguridadcalidad;

import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HomeControllerTests {

    private final HomeController homeController = new HomeController();

    private static final String COURSE_NAME = "Seguridad y Calidad en el Desarrollo";

    // -------------------------------------------------------------------------
    // /home
    // -------------------------------------------------------------------------

    @Test
    void homeShouldReturnHomeView() {
        Model model = new ExtendedModelMap();
        String view = homeController.home(COURSE_NAME, model);
        assertEquals("home", view);
    }

    @Test
    void homeShouldAddNameAttributeToModel() {
        Model model = new ExtendedModelMap();
        homeController.home("Mi Clinica", model);
        assertEquals("Mi Clinica", model.getAttribute("name"));
    }

    @Test
    void homeShouldUseDefaultNameWhenNotProvided() {
        Model model = new ExtendedModelMap();
        homeController.home(COURSE_NAME, model);
        assertEquals(COURSE_NAME, model.getAttribute("name"));
    }

    // -------------------------------------------------------------------------
    // /
    // -------------------------------------------------------------------------

    @Test
    void rootShouldReturnHomeView() {
        Model model = new ExtendedModelMap();
        String view = homeController.root(COURSE_NAME, model);
        assertEquals("home", view);
    }

    @Test
    void rootShouldAddNameAttributeToModel() {
        Model model = new ExtendedModelMap();
        homeController.root("Otro Nombre", model);
        assertEquals("Otro Nombre", model.getAttribute("name"));
    }

    @Test
    void rootShouldUseDefaultNameWhenNotProvided() {
        Model model = new ExtendedModelMap();
        homeController.root(COURSE_NAME, model);
        assertEquals(COURSE_NAME, model.getAttribute("name"));
    }
}
