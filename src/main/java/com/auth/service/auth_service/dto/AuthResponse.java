package com.auth.service.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse {
    private String message;

    // Nombre de usuario
    private String username;

    // Rol asignado al usuario (ej: ADMIN, USER)
    private String role;

    // Token JWT generado para la sesión del usuario
    private String token;

    /**
     * Constructor por defecto.
     */
    public AuthResponse() {
    }

    /**
     * Constructor solo el mensaje.
     * Se usará para respuestas simples o errores.
     *
     * @param message Mensaje descriptivo de la respuesta
     */
    public AuthResponse(String message) {
        this.message = message;
    }

    /**
     * Constructor del DTOsin token.
     * Se usará para endpoints que no necesiten token JWT.
     *
     * @param message  Mensaje descriptivo de la respuesta
     * @param username Nombre del usuario
     * @param role     Rol del usuario
     */
    public AuthResponse(String message, String username, String role) {
        this.message = message;
        this.username = username;
        this.role = role;
    }

    /**
     * Constructor del DTO
     *
     * @param message  Mensaje descriptivo de la respuesta
     * @param username Nombre del usuario autenticado
     * @param role     Rol del usuario
     * @param token    Token JWT para autorización
     */
    public AuthResponse(String message, String username, String role, String token) {
        this.message = message;
        this.username = username;
        this.role = role;
        this.token = token;
    }

    /**
     * Obtiene el mensaje de la respuesta.
     *
     * @return El mensaje descriptivo
     */
    public String getMessage() {
        return message;
    }

    /**
     * Establece el mensaje de la respuesta.
     *
     * @param message El mensaje a establecer
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Obtiene el nombre de usuario.
     *
     * @return El nombre de usuario
     */
    public String getUsername() {
        return username;
    }

    /**
     * Establece el nombre de usuario.
     *
     * @param username El nombre de usuario a establecer
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtiene el rol del usuario.
     *
     * @return El rol del usuario
     */
    public String getRole() {
        return role;
    }

    /**
     * Establece el rol del usuario.
     *
     * @param role El rol a establecer
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Obtiene el token JWT.
     *
     * @return El token JWT
     */
    public String getToken() {
        return token;
    }

    /**
     * Establece el token JWT.
     *
     * @param token El token JWT a establecer
     */
    public void setToken(String token) {
        this.token = token;
    }
}
