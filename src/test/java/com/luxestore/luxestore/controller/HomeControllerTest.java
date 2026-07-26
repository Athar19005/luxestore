package com.luxestore.luxestore.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import com.luxestore.luxestore.model.Product;
import com.luxestore.luxestore.model.User;
import com.luxestore.luxestore.repository.ProductRepository;
import com.luxestore.luxestore.repository.UserRepository;

class HomeControllerTest {

    private HomeController controller;
    private ProductRepository productRepo;
    private UserRepository userRepo;

    @BeforeEach
    void setUp() {
        controller = new HomeController();
        productRepo = mock(ProductRepository.class);
        userRepo = mock(UserRepository.class);
        controller.productRepo = productRepo;
        controller.userRepo = userRepo;
    }

    @Test
    void homeShouldLoadAllProductsWhenCategoryIsMissing() {
        Product product = new Product();
        product.setName("Rolex Watch");
        when(productRepo.findAll()).thenReturn(List.of(product));

        Model model = new ConcurrentModel();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userName", "Alice");

        String view = controller.home(null, model, session);

        assertEquals("index", view);
        assertEquals(List.of(product), model.getAttribute("products"));
        assertEquals("All", model.getAttribute("selectedCategory"));
        assertEquals("Alice", model.getAttribute("userName"));
        verify(productRepo).findAll();
        verify(productRepo, never()).findByCategory(anyString());
    }

    @Test
    void loginShouldRedirectToHomeWhenCredentialsAreValid() {
        User user = new User();
        user.setId(12);
        user.setFullName("Alice");
        when(userRepo.findByEmailAndPassword("alice@example.com", "secret123")).thenReturn(user);

        Model model = new ConcurrentModel();
        MockHttpSession session = new MockHttpSession();

        String view = controller.login("alice@example.com", "secret123", session, model);

        assertEquals("redirect:/", view);
        assertEquals("Alice", session.getAttribute("userName"));
        assertEquals(12, session.getAttribute("userId"));
        assertTrue(model.asMap().isEmpty());
    }

    @Test
    void loginShouldReturnLoginViewWhenCredentialsAreInvalid() {
        when(userRepo.findByEmailAndPassword("invalid@example.com", "wrongpass")).thenReturn(null);

        Model model = new ConcurrentModel();
        MockHttpSession session = new MockHttpSession();

        String view = controller.login("invalid@example.com", "wrongpass", session, model);

        assertEquals("login", view);
        assertEquals("Invalid email or password!", model.getAttribute("error"));
    }

    @Test
    void registerShouldSaveUserAndShowSuccessMessage() {
        when(userRepo.existsByEmail("new@example.com")).thenReturn(false);

        Model model = new ConcurrentModel();

        String view = controller.register("Alice", "new@example.com", "secret123", "secret123", model);

        assertEquals("login", view);
        assertEquals("Registration successful! Please login.", model.getAttribute("success"));
        verify(userRepo).save(any(User.class));
    }

    @Test
    void logoutShouldInvalidateSessionAndRedirectToLogin() {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userName", "Alice");

        String view = controller.logout(session);

        assertEquals("redirect:/login", view);
        assertTrue(session.isInvalid());
    }
}
