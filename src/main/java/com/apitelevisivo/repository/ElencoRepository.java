package com.apitelevisivo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apitelevisivo.model.Elenco;

@Repository
public interface ElencoRepository extends JpaRepository<Elenco, Long> {

}