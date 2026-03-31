package com.example.demo.controller;

import com.example.demo.model.Users;
import com.example.demo.repository.UsersRepository;
import com.example.demo.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ Register
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (!user.getRole().startsWith("ROLE_")) {
            user.setRole("ROLE_" + user.getRole().toUpperCase());
        }
        Users saved = usersRepository.save(user);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "User registered successfully");
        response.put("userId",  saved.getId());
        response.put("username", saved.getUsername());
        response.put("role",    saved.getRole().replace("ROLE_", ""));
        return ResponseEntity.ok(response);
    }

    // ✅ Login — token + role + customerId सगळे पाठवा
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Users loginUser) {
        Users user = usersRepository
                .findByUsername(loginUser.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginUser.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

        // ✅ Role strip करा — "ROLE_ADMIN" → "ADMIN"
        String cleanRole = user.getRole().replace("ROLE_", "");

        // ✅ सगळे एकत्र पाठवा
        Map<String, Object> response = new HashMap<>();
        response.put("token",      token);
        response.put("role",       cleanRole);           // "ADMIN"
        response.put("username",   user.getUsername());
        response.put("customerId", user.getId());        // ✅ Customer ID
        response.put("userId",     user.getId());

        return ResponseEntity.ok(response);
    }

    // ✅ Get All Users
    @GetMapping
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }
}