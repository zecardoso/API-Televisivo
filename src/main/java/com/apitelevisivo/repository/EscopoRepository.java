package com.apitelevisivo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apitelevisivo.model.Escopo;

@Repository
public interface EscopoRepository extends JpaRepository<Escopo, Long> {

}