package com.auth.service.auth_service.dto;

public class RegisterRequest {

    /** Nombre de usuario único para el nuevo registro. */
    private String username;

    /** Contraseña para la nueva cuenta. */
    private String password;

    /**
     * Constructor por defecto.
     * Requerido para la deserialización JSON.
     */
    public RegisterRequest() {
    }

    /**
     * Constructor con credenciales de registro.
     *
     * @param username Nombre de usuario deseado
     * @param password Contraseña para la cuenta
     */
    public RegisterRequest(String username, String password) {
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
     * Obtiene la contraseña.
     *
     * @return La contraseña
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña.
     *
     * @param password La contraseña a establecer
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
