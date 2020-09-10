package com.apitelevisivo.service;

import java.util.Optional;

import com.apitelevisivo.model.Usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioService extends GenericService<Usuario, Long> {

    Optional<Usuario> findUsuarioByEmail(String email);
    Optional<Usuario> loginUsuarioByEmail(String email);
	void updateLoginUsuario(Usuario usuario);
	void blockedUsuario(Usuario usuario);
	void updateFaileAccess(Usuario usuario);
	Page<Usuario> findAll(Pageable pageable);
	Page<Usuario> findAllByName(String nome, Pageable pageable);
}