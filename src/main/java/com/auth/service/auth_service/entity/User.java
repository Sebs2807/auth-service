package com.auth.service.auth_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    // Identificador único auto-generado del usuario
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre de usuario único en el sistema
    @Column(unique = true, nullable = false)
    private String username;

    // Contraseña encriptada del usuario
    @Column(nullable = false)
    private String password;

    // Rol del usuario (ej: ROLE_USER, ROLE_ADMIN)
    @Column(nullable = false)
    private String role;

    /**
     * Constructor por defecto.
     * Requerido por JPA para la creación de instancias.
     */
    public User() {
    }

    /**
     * Constructor con todos los campos requeridos.
     *
     * @param username Nombre de usuario único
     * @param password Contraseña (debe estar encriptada)
     * @param role     Rol asignado al usuario
     */
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Obtiene el ID del usuario.
     *
     * @return El identificador único
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el ID del usuario.
     *
     * @param id El identificador a establecer
     */
    public void setId(Long id) {
        this.id = id;
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
     * Obtiene la contraseña encriptada.
     *
     * @return La contraseña encriptada
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña.
     *
     * @param password La contraseña a establecer (debe estar encriptada)
     */
    public void setPassword(String password) {
        this.password = password;
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
}
