package com.marvel.benefits_planning.controller;

import com.marvel.benefits_planning.dto.AuthResponse;
import com.marvel.benefits_planning.dto.LoginRequest;
import com.marvel.benefits_planning.dto.RegisterRequest;
import com.marvel.benefits_planning.entity.AppUser;
import com.marvel.benefits_planning.entity.Role;
import com.marvel.benefits_planning.exception.BusinessRuleException;
import com.marvel.benefits_planning.repository.AppUserRepository;
import com.marvel.benefits_planning.security.CustomUserDetailsService;
import com.marvel.benefits_planning.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new BusinessRuleException("Username already taken");
        }
        AppUser user = new AppUser();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.EMPLOYEE);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "User registered"));
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        UserDetails user = userDetailsService.loadUserByUsername(request.username());
        return new AuthResponse(jwtService.generateToken(user));
    }
}