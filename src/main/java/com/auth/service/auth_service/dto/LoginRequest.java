package com.auth.service.auth_service.dto;

public class LoginRequest {

    private String username;
    private String password;

    public LoginRequest() {
    }

    /**
     * Constructor con credenciales de usuario.
     *
     * @param username Nombre de usuario para autenticación
     * @param password Contraseña del usuario
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
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
     * Obtiene la contraseña del usuario.
     *
     * @return La contraseña
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param password La contraseña a establecer
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
