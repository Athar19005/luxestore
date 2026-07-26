package com.luxestore.luxestore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luxestore.luxestore.model.Product;
import com.luxestore.luxestore.model.User;
import com.luxestore.luxestore.repository.ProductRepository;
import com.luxestore.luxestore.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    ProductRepository productRepo;

    @Autowired
    UserRepository userRepo;

    @GetMapping("/")
    public String home(@RequestParam(required = false) String category,
                       Model model, HttpSession session) {
        List<Product> products;
        if (category == null || category.equals("All")) {
            products = productRepo.findAll();
        } else {
            products = productRepo.findByCategory(category);
        }
        model.addAttribute("products", products);
        model.addAttribute("selectedCategory", category != null ? category : "All");
        model.addAttribute("userName", session.getAttribute("userName"));
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session, Model model) {
        User user = userRepo.findByEmailAndPassword(email, password);
        if (user != null) {
            session.setAttribute("userName", user.getFullName());
            session.setAttribute("userId", user.getId());
            return "redirect:/";
        } else {
            model.addAttribute("error", "Invalid email or password!");
            return "login";
        }
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String fullName,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String confirmPassword,
                           Model model) {
        if (fullName.length() < 3) {
            model.addAttribute("error", "Name must be at least 3 characters!");
            return "register";
        }
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match!");
            return "register";
        }
        if (password.length() < 6) {
            model.addAttribute("error", "Password must be at least 6 characters!");
            return "register";
        }
        if (userRepo.existsByEmail(email)) {
            model.addAttribute("error", "Email already registered!");
            return "register";
        }
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(password);
        userRepo.save(user);
        model.addAttribute("success", "Registration successful! Please login.");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}