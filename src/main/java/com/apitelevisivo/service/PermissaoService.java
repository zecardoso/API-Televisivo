package com.apitelevisivo.service;

import com.apitelevisivo.model.Permissao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PermissaoService extends GenericService<Permissao, Long> {

	Page<Permissao> findAll(Pageable pageable);
	Page<Permissao> findAllByName(String nome, Pageable pageable);
}