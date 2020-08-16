package com.apitelevisivo.service;

import java.util.Optional;

import com.apitelevisivo.model.Usuario;

public interface UsuarioService extends GenericService<Usuario, Long> {

    Optional<Usuario> findUsuarioByEmail(String email);
    Optional<Usuario> loginUsuarioByEmail(String email);
	void updateLoginUsuario(Usuario usuario);
	void blockedUsuario(Usuario usuario);
	void updateFaileAccess(Usuario usuario);
}