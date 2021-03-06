package com.apitelevisivo.security;

import org.springframework.security.core.userdetails.User;

import com.apitelevisivo.model.Usuario;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class UsuarioSistema extends User {

    private static final long serialVersionUID = 6297924943836319733L;

    @EqualsAndHashCode.Include
    private Usuario usuario;

    public UsuarioSistema(Usuario usuario) {
        super(usuario.getUsername(), usuario.getPassword(), usuario.isEnabled(), usuario.isAccountNonExpired(), usuario.isCredentialsNonExpired(), usuario.isAccountNonLocked(), usuario.getAuthorities());
        this.usuario = usuario;
    }
}