package com.apitelevisivo.service;

import com.apitelevisivo.model.Serie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SerieService extends GenericService<Serie, Long> {

	Page<Serie> findAll(Pageable pageable);
	Page<Serie> findAllByName(String nome, Pageable pageable);
}