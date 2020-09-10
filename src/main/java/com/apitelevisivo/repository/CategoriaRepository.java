package com.apitelevisivo.repository;

import com.apitelevisivo.model.Categoria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query("SELECT u FROM Categoria u WHERE u.nome LIKE LOWER(CONCAT('%', :nome, '%'))")
	Page<Categoria> findAllByName(@Param("nome") String nome, Pageable pageable);
}