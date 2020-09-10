package com.apitelevisivo.service;

import com.apitelevisivo.model.Categoria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoriaService extends GenericService<Categoria, Long> {

	Page<Categoria> findAll(Pageable pageable);
	Page<Categoria> findAllByName(String nome, Pageable pageable);
}