package com.televisivo.repository;

import com.televisivo.model.Elenco;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElencoRepository extends JpaRepository<Elenco, Long> {

}