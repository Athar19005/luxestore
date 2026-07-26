package com.luxestore.luxestore.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void shouldExposeConfiguredUserProperties() {
        User user = new User();

        user.setId(11);
        user.setFullName("Alice Johnson");
        user.setEmail("alice@example.com");
        user.setPassword("secret123");

        assertEquals(11, user.getId());
        assertEquals("Alice Johnson", user.getFullName());
        assertEquals("alice@example.com", user.getEmail());
        assertEquals("secret123", user.getPassword());
    }
}
