package com.apitelevisivo.service;

import com.apitelevisivo.model.Elenco;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ElencoService extends GenericService<Elenco, Long>{

	Page<Elenco> findAll(Pageable pageable);
}