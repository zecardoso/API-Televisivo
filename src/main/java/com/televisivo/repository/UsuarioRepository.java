package com.televisivo.repository;

import com.televisivo.model.Usuario;
import com.televisivo.repository.query.UsuarioQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioQuery {

}