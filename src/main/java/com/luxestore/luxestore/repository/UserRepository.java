package com.luxestore.luxestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luxestore.luxestore.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);
}