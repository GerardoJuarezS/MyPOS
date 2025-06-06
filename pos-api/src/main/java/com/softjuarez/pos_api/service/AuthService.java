package com.softjuarez.pos_api.service;

import com.softjuarez.pos_api.dto.RegisterRequest;
import com.softjuarez.pos_api.model.User;
import com.softjuarez.pos_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ✅ Agregamos esta línea para inyectar UserDetailsService
    private final UserDetailsService userDetailsService;

    public boolean register(RegisterRequest request) {
        Optional<User> existingUser = userRepository.findByUsername(request.getUsername());
        if (existingUser.isPresent()) {
            return false; // Usuario ya existe
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("ROLE_" + request.getRole().toUpperCase())
                .build();

        userRepository.save(user);
        return true;
    }

    public void authenticate(String username, String password) {
        UserDetails userDetails = loadUserByUsername(username);
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }
    }

    // ✅ Ahora sí puedes usar el servicio inyectado
    public UserDetails loadUserByUsername(String username) {
        return userDetailsService.loadUserByUsername(username);
    }
}