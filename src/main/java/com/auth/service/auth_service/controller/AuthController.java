package com.auth.service.auth_service.controller;

import com.auth.service.auth_service.dto.AuthResponse;
import com.auth.service.auth_service.dto.LoginRequest;
import com.auth.service.auth_service.dto.RegisterRequest;
import com.auth.service.auth_service.entity.User;
import com.auth.service.auth_service.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository repository;

    public AuthController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        // Verificar si el usuario ya existe
        if (repository.existsByUsername(request.getUsername())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new AuthResponse("El usuario ya existe"));
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword()); // Sin encriptar por ahora
        user.setRole("USER");

        repository.save(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new AuthResponse("Usuario registrado exitosamente", user.getUsername(), user.getRole()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        Optional<User> userOptional = repository.findByUsername(request.getUsername());

        if (userOptional.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse("Usuario no encontrado"));
        }

        User user = userOptional.get();

        // Verificación simple de contraseña (sin encriptación por ahora)
        if (!user.getPassword().equals(request.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse("Contraseña incorrecta"));
        }

        return ResponseEntity
                .ok(new AuthResponse("Login exitoso", user.getUsername(), user.getRole()));
    }
}
