package com.apitelevisivo.repository;

import com.apitelevisivo.model.Servico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {

    @Query("SELECT u FROM Servico u WHERE u.nome LIKE LOWER(CONCAT('%', :nome, '%'))")
	Page<Servico> findAllByName(@Param("nome") String nome, Pageable pageable);
}