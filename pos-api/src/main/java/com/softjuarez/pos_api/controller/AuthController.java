package com.softjuarez.pos_api.controller;

import com.softjuarez.pos_api.dto.*;
import com.softjuarez.pos_api.service.AuthService;
import com.softjuarez.pos_api.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.userdetails.UserDetails;
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints para login y registro de usuarios")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/login")
    @Operation(summary = "Inicia sesión y devuelve un token JWT")
    public ResponseEntity<JwtResponse> login(@RequestBody AuthRequest request) {
        try {
            authService.authenticate(request.getUsername(), request.getPassword());
        } catch (Exception e) {
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }

        UserDetails userDetails = authService.loadUserByUsername(request.getUsername());
        String jwt = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("/register")
    @Operation(summary = "Registra un nuevo usuario")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        boolean isRegistered = authService.register(request);
        if (isRegistered) {
            return ResponseEntity.ok("Usuario registrado exitosamente");
        } else {
            return ResponseEntity.badRequest().body("El nombre de usuario ya está en uso");
        }
    }
}