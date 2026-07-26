package com.luxestore.luxestore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luxestore.luxestore.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategory(String category);
}