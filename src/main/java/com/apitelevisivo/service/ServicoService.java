package com.apitelevisivo.service;

import com.apitelevisivo.model.Servico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServicoService extends GenericService<Servico, Long> {

	Page<Servico> findAll(Pageable pageable);
	Page<Servico> findAllByName(String nome, Pageable pageable);
}