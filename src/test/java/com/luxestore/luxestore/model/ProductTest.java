package com.luxestore.luxestore.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void shouldExposeConfiguredProductProperties() {
        Product product = new Product();

        product.setId(7);
        product.setName("Rolex Watch");
        product.setDescription("Luxury timepiece");
        product.setPrice(12999.99);
        product.setCategory("Accessories");
        product.setImageUrl("/images/rolex.jpg");
        product.setRating(4.8);

        assertEquals(7, product.getId());
        assertEquals("Rolex Watch", product.getName());
        assertEquals("Luxury timepiece", product.getDescription());
        assertEquals(12999.99, product.getPrice());
        assertEquals("Accessories", product.getCategory());
        assertEquals("/images/rolex.jpg", product.getImageUrl());
        assertEquals(4.8, product.getRating());
    }
}
