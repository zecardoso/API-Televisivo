package com.televisivo.repository;

import com.televisivo.model.Escopo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EscopoRepository extends JpaRepository<Escopo, Long> {

}