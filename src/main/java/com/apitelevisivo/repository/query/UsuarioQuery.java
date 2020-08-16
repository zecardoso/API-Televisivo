package com.apitelevisivo.repository.query;

import java.util.List;
import java.util.Optional;

import com.apitelevisivo.model.Permission;
import com.apitelevisivo.model.Usuario;

public interface UsuarioQuery {

    Optional<Usuario> buscarAtivoPorEmail(String email);
    Optional<Usuario> findUsuarioByEmail(String email);
    Optional<Usuario> loginUsuarioByEmail(String email);
    List<Permission> findRolePermissaoByUsuarioId(Long id);
}