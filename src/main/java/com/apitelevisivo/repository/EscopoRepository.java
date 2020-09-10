package com.apitelevisivo.repository;

import com.apitelevisivo.model.Escopo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EscopoRepository extends JpaRepository<Escopo, Long> {

    @Query("SELECT u FROM Escopo u WHERE u.nome LIKE LOWER(CONCAT('%', :nome, '%'))")
	Page<Escopo> findAllByName(@Param("nome") String nome, Pageable pageable);
}