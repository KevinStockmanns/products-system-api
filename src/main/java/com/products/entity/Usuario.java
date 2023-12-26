package com.products.entity;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.products.dto.auth.AuthRegisterDTO;
import com.products.utils.Util;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data @AllArgsConstructor @NoArgsConstructor
public class Usuario implements UserDetails{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String apellido;

    @Column(length = 20)
    private String telefono;
    
    private String correo;

    private Boolean estado;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_ultimo_login")
    private LocalDateTime ultimoLogin;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    private String password;
    
    @ManyToOne
    @JoinColumn(name = "fk_rol")
    private Rol rol;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rol.getNombre()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.estado;
    }

    @Override
    public String getUsername() {
        return this.correo;
    }

    public Usuario(@Valid AuthRegisterDTO datos) {
        this.nombre = Util.formatTitle(datos.nombre());
        this.apellido = Util.formatTitle(datos.apellido());
        this.telefono = datos.telefono();
        this.correo = datos.correo();
        this.estado = true;
        this.fechaCreacion = LocalDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"));
        this.ultimoLogin = LocalDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"));
        this.fechaNacimiento = datos.fechaNacimiento();
    }
}
