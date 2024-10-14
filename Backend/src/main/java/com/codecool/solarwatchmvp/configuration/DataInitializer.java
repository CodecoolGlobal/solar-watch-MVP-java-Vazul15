package com.codecool.solarwatchmvp.configuration;

import com.codecool.solarwatchmvp.model.entity.Role;
import com.codecool.solarwatchmvp.model.entity.UserEntity;
import com.codecool.solarwatchmvp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.findByEmail("admin@example.com").isPresent()) {
            Set<Role> roles = new HashSet<>();
            roles.add(Role.ROLE_ADMIN);
            String rawPassword = "admin123";
            String hashedPassword = passwordEncoder.encode(rawPassword);
            UserEntity admin = new UserEntity("admin@example.com", hashedPassword, roles);
            userRepository.save(admin);
            System.out.println("Admin user created");
        } else {
            System.out.println("Admin user already exists");
        }
    }
}
