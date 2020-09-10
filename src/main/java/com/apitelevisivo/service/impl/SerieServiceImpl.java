package com.apitelevisivo.service.impl;

import java.util.List;

import com.apitelevisivo.model.Serie;
import com.apitelevisivo.repository.SerieRepository;
import com.apitelevisivo.service.SerieService;
import com.apitelevisivo.service.exceptions.EntidadeEmUsoException;
import com.apitelevisivo.service.exceptions.SerieNaoCadastradaException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SerieServiceImpl implements SerieService {

    @Autowired
    private SerieRepository serieRepository;

    @Override
	@Transactional(readOnly = true)
    public List<Serie> findAll() {
        return serieRepository.findAll();
    }

    @Override
    public Serie save(Serie serie) {
        serie.setQtdTemporadas(serie.getTemporadas().size());
        return serieRepository.save(serie);
    }

    @Override
    public Serie update(Serie serie) {
        return save(serie);
    }

    @Override
	@Transactional(readOnly = true)
    public Serie getOne(Long id) {
        return serieRepository.getOne(id);
    }

    @Override
    public Serie findById(Long id) {
        return serieRepository.findById(id).orElseThrow(() -> new SerieNaoCadastradaException(id));
    }

    @Override
    public void deleteById(Long id) {
		try {
			serieRepository.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("A serie de código %d não pode ser removida!", id));
		} catch (EmptyResultDataAccessException e){
			throw new SerieNaoCadastradaException(String.format("A serie com o código %d não foi encontrada!", id));
		}
    }

    @Override
    public Page<Serie> findAll(Pageable pageable) {
        return serieRepository.findAll(pageable);
    }

    @Override
    public Page<Serie> findAllByName(String nome, Pageable pageable) {
        return serieRepository.findAllByName(nome, pageable);
    }
}