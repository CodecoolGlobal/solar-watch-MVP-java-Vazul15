package com.codecool.solarwatchmvp.controller;

import com.codecool.solarwatchmvp.model.DTO.response.UserResponse;
import com.codecool.solarwatchmvp.model.entity.Role;
import com.codecool.solarwatchmvp.model.entity.UserEntity;
import com.codecool.solarwatchmvp.model.payload.JwtResponse;
import com.codecool.solarwatchmvp.model.payload.UserRequest;
import com.codecool.solarwatchmvp.repository.UserRepository;
import com.codecool.solarwatchmvp.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Autowired
    public UserController(UserRepository userRepository, PasswordEncoder encoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public UserResponse createUser(@RequestBody UserRequest signUpRequest) {
        UserEntity user = new UserEntity(signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                Set.of(Role.ROLE_USER));
        UserEntity savedUser = userRepository.save(user);

        return new UserResponse(savedUser.getId());
    }

    @PostMapping("/signin")
    public JwtResponse authenticateUser(@RequestBody UserRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        User userDetails = (User) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .toList();

        return new JwtResponse(jwt, userDetails.getUsername(), roles);
    }
}
