package com.apitelevisivo.repository;

import com.apitelevisivo.model.Permissao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

    @Query("SELECT u FROM Permissao u WHERE u.nome LIKE LOWER(CONCAT('%', :nome, '%'))")
	Page<Permissao> findAllByName(@Param("nome") String nome, Pageable pageable);
}