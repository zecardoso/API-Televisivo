package com.apitelevisivo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.apitelevisivo.model.Usuario;
import com.apitelevisivo.repository.query.UsuarioQuery;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioQuery {

    @Query("SELECT distinct u FROM Usuario u LEFT JOIN FETCH u.roles")
	List<Usuario> findAllUsuarios();

    @Query("SELECT u FROM Usuario u WHERE u.username LIKE LOWER(CONCAT('%', :username, '%'))")
	Page<Usuario> findAllByName(@Param("username") String username, Pageable pageable);
}