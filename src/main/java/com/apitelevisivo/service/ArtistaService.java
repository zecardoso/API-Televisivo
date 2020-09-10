package com.apitelevisivo.service;

import com.apitelevisivo.model.Artista;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArtistaService extends GenericService<Artista, Long> {

	Page<Artista> findAll(Pageable pageable);
	Page<Artista> findAllByName(String nome, Pageable pageable);
}