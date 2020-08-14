package com.televisivo.repository.query;

import java.util.List;
import java.util.Optional;

import com.televisivo.model.Permission;
import com.televisivo.model.Usuario;

public interface UsuarioQuery {

    Optional<Usuario> buscarAtivoPorEmail(String email);
    Optional<Usuario> findUsuarioByEmail(String email);
    Optional<Usuario> loginUsuarioByEmail(String email);
    List<Permission> findRolePermissaoByUsuarioId(Long id);
}