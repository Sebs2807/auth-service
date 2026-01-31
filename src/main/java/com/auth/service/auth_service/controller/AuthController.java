package com.auth.service.auth_service.controller;

import com.auth.service.auth_service.dto.AuthResponse;
import com.auth.service.auth_service.dto.LoginRequest;
import com.auth.service.auth_service.dto.RegisterRequest;
import com.auth.service.auth_service.entity.User;
import com.auth.service.auth_service.repository.UserRepository;
import com.auth.service.auth_service.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserRepository repository,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil,
                          AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Registro de nuevo usuario
     * @param request Datos de registro
     * @return Respuesta con token JWT
     */
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
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");

        repository.save(user);

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new AuthResponse("Usuario registrado exitosamente", user.getUsername(), user.getRole(), token));
    }

    /**
     * Registro de nuevo administrador.
     * Solo accesible por usuarios con rol ADMIN.
     *
     * @param request Datos de registro del nuevo admin
     * @return Respuesta con información del admin creado
     */
    @PostMapping("/register/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthResponse> registerAdmin(@RequestBody RegisterRequest request) {
        // Verificar si el usuario ya existe
        if (repository.existsByUsername(request.getUsername())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new AuthResponse("El usuario ya existe"));
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ADMIN");

        repository.save(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new AuthResponse("Administrador registrado exitosamente", user.getUsername(), user.getRole()));
    }

    /**
     * Login de usuario
     * @param request Datos de login
     * @return Respuesta con token JWT
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

        return ResponseEntity
                .ok(new AuthResponse("Login exitoso", user.getUsername(), user.getRole(), token));
    }


    /**
     * Valida el token JWT
     * @param authHeader Header de autorización con el token
     * @return Respuesta de validación
     */
    @GetMapping("/validate")
    public ResponseEntity<AuthResponse> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse("Token no proporcionado"));
        }

        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        String role = jwtUtil.extractRole(token);

        if (jwtUtil.validateToken(token, username)) {
            return ResponseEntity.ok(new AuthResponse("Token válido", username, role, token));
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new AuthResponse("Token inválido o expirado"));
    }
}
