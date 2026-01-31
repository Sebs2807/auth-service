package com.auth.service.auth_service.exception;

import com.auth.service.auth_service.dto.AuthResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones de credenciales inválidas.
     * Se activa cuando el usuario proporciona usuario/contraseña incorrectos.
     *
     * @param e Excepción de credenciales incorrectas
     * @return Respuesta HTTP 401 (UNAUTHORIZED) con mensaje de error
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<AuthResponse> handleBadCredentials(BadCredentialsException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new AuthResponse("Credenciales inválidas"));
    }

    /**
     * Maneja excepciones cuando el usuario no existe.
     * Se activa cuando se busca un usuario que no está registrado.
     *
     * @param e Excepción de usuario no encontrado
     * @return Respuesta HTTP 404 (NOT_FOUND) con mensaje de error
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<AuthResponse> handleUserNotFound(UsernameNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new AuthResponse(e.getMessage()));
    }

    /**
     * Maneja cualquier excepción no capturada específicamente.
     * Actúa como fallback para errores inesperados del servidor.
     *
     * @param e Excepción genérica
     * @return Respuesta HTTP 500 (INTERNAL_SERVER_ERROR) con mensaje de error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<AuthResponse> handleGenericException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new AuthResponse("Error interno del servidor: " + e.getMessage()));
    }
}
