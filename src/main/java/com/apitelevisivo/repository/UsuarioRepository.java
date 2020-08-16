package com.apitelevisivo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apitelevisivo.model.Usuario;
import com.apitelevisivo.repository.query.UsuarioQuery;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioQuery {

}