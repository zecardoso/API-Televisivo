package com.apitelevisivo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apitelevisivo.model.Episodio;

@Repository
public interface EpisodioRepository extends JpaRepository<Episodio, Long> {

}