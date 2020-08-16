package com.apitelevisivo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apitelevisivo.model.Episodio;
import com.apitelevisivo.repository.EpisodioRepository;
import com.apitelevisivo.service.EpisodioService;
import com.apitelevisivo.service.exceptions.EntidadeEmUsoException;
import com.apitelevisivo.service.exceptions.EpisodioNaoCadastradoException;

@Service
@Transactional
public class EpisodioServiceImpl implements EpisodioService {

    @Autowired
    private EpisodioRepository episodioRepository;

    @Override
	@Transactional(readOnly = true)
    public List<Episodio> findAll() {
        return episodioRepository.findAll();
    }

    @Override
    public Episodio save(Episodio episodio) {
        return episodioRepository.save(episodio);
    }

    @Override
    public Episodio update(Episodio episodio) {
        return episodioRepository.save(episodio);
    }

    @Override
	@Transactional(readOnly = true)
    public Episodio getOne(Long id) {
        return episodioRepository.getOne(id);
    }

    @Override
    public Episodio findById(Long id) {
        return episodioRepository.findById(id).orElseThrow(() -> new EpisodioNaoCadastradoException(id));
    }

    @Override
    public void deleteById(Long id) {
		try {
			episodioRepository.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("O artista de código %d não pode ser removido!", id));
		} catch (EmptyResultDataAccessException e){
			throw new EpisodioNaoCadastradoException(String.format("O episodio com o código %d não foi encontrado!", id));
		}
    }
}