package com.apitelevisivo.service;

import com.apitelevisivo.model.Escopo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EscopoService extends GenericService<Escopo, Long> {

	Page<Escopo> findAll(Pageable pageable);
	Page<Escopo> findAllByName(String nome, Pageable pageable);
}