package com.apitelevisivo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apitelevisivo.model.Temporada;

@Repository
public interface TemporadaRepository extends JpaRepository<Temporada, Long> {

}