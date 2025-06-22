package com.duoc.backend;

import java.util.Collection;
import java.util.Collections; // Necesario para Collections.emptyList()

import org.springframework.security.core.GrantedAuthority; // Posiblemente necesario para roles si los implementas
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue; // Necesario si manejas roles
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
// Importaciones JPA según tu versión de Jakarta EE (si usas javax.persistence, cámbialo)
// import javax.persistence.Entity;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;

@Entity
@Table(name = "users") // Buena práctica: evita "user" como nombre de tabla, puede entrar en conflicto con palabras clave SQL
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Usa IDENTITY para ID auto-incremental con MySQL
    private Integer id;

    private String username;

    private String email;

    private String password; // Aquí se almacenará la contraseña *hasheada*

    // --- Constructores (Opcional, pero buena práctica) ---
    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // --- Getters y Setters ---
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // Nota: getUsername() es parte de UserDetails y ya existe
    @Override // Importante: Asegúrate de que esto esté sobrescribiendo correctamente el método de UserDetails
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Nota: getPassword() es parte de UserDetails y ya existe
    @Override // Importante: Asegúrate de que esto esté sobrescribiendo correctamente el método de UserDetails
    public String getPassword() {
        return password; // Esto debería ser la contraseña hasheada de la base de datos
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // --- Implementaciones de la Interfaz UserDetails ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Por ahora, devolveremos una lista vacía.
        // Si implementas roles (ej., "ROLE_ADMIN", "ROLE_USER"),
        // deberías devolver una colección de objetos SimpleGrantedAuthority aquí.
        // Ejemplo: return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        return Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() {
        // Devuelve true si la cuenta del usuario no ha expirado.
        // Implementa tu propia lógica si tienes expiración de cuentas.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Devuelve true si la cuenta del usuario no está bloqueada.
        // Implementa tu propia lógica si tienes bloqueo de cuentas (ej., después de demasiados intentos de inicio de sesión fallidos).
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Devuelve true si las credenciales del usuario (contraseña) no han expirado.
        // Implementa tu propia lógica si requieres cambios de contraseña después de un cierto período.
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Devuelve true si el usuario está habilitado (activo).
        // Implementa tu propia lógica si tienes un estado activo/inactivo para los usuarios.
        return true;
    }
}