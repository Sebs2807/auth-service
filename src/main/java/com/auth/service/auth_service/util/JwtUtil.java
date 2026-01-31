package com.auth.service.auth_service.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    // Clave secreta para firmar los tokens (configurada en application.properties)
    @Value("${jwt.secret}")
    private String secret;

    // Tiempo de expiración del token en milisegundos.
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * Genera la clave de firma HMAC a partir del secreto configurado.
     *
     * @return Clave criptográfica para firmar/verificar tokens
     */
    private Key getSigningKey() {
        byte[] keyBytes = secret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Genera un nuevo token JWT para un usuario.
     *
     * @param username Nombre de usuario (será el subject del token)
     * @param role     Rol del usuario a incluir en los claims
     * @return Token JWT firmado como String
     */
    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return createToken(claims, username);
    }

    /**
     * Crea el token JWT con los claims y subject especificados.
     *
     * @param claims  Claims
     * @param subject El subject del token
     * @return Token JWT firmado y codificado
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extrae el nombre de usuario del token.
     *
     * @param token Token JWT
     * @return Nombre de usuario contenido en el token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrae el rol del usuario del token.
     *
     * @param token Token JWT
     * @return Rol del usuario contenido en los claims
     */
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    /**
     * Extrae la fecha de expiración del token.
     *
     * @param token Token JWT
     * @return Fecha de expiración del token
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrae un claim específico del token usando una función extractora.
     *
     * @param <T>            Tipo del valor a extraer
     * @param token          Token JWT
     * @param claimsResolver Función para extraer el claim deseado
     * @return Valor del claim extraído
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrae todos los claims del token JWT.
     *
     * @param token Token JWT
     * @return Objeto Claims con toda la información del token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Verifica si el token ha expirado.
     *
     * @param token Token JWT
     * @return true si el token está expirado, false en caso contrario
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Valida un token JWT verificando el usuario y la expiración.
     *
     * @param token    Token JWT a validar
     * @param username Nombre de usuario esperado
     * @return true si el token es válido para el usuario, false en caso contrario
     */
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}
