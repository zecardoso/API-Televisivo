package com.televisivo.repository;

import com.televisivo.model.Temporada;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemporadaRepository extends JpaRepository<Temporada, Long> {

}