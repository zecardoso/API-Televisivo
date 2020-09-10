package com.apitelevisivo.service.impl;

import java.util.List;

import com.apitelevisivo.model.Artista;
import com.apitelevisivo.repository.ArtistaRepository;
import com.apitelevisivo.service.ArtistaService;
import com.apitelevisivo.service.exceptions.ArtistaNaoCadastradoException;
import com.apitelevisivo.service.exceptions.EntidadeEmUsoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArtistaServiceImpl implements ArtistaService {

    @Autowired
    private ArtistaRepository artistaRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<Artista> findAll() {
        return artistaRepository.findAll();
    }

    @Override
    public Artista save(Artista artista) {
        return artistaRepository.save(artista);
    }

    @Override
    public Artista update(Artista artista) {
        return artistaRepository.save(artista);
    }

    @Override
    @Transactional(readOnly = true)
    public Artista getOne(Long id) {
		return artistaRepository.getOne(id);
    }

    @Override
    public Artista findById(Long id) {
		return artistaRepository.findById(id).orElseThrow(()-> new ArtistaNaoCadastradoException(id));
    }

    @Override
	public void deleteById(Long id) {
		try {
			artistaRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("O artista de código %d não pode ser removido!", id));
		} catch (EmptyResultDataAccessException e) {
			throw new ArtistaNaoCadastradoException(String.format("O artista com o código %d não foi encontrado!", id));
		}
	}

    @Override
    public Page<Artista> findAll(Pageable pageable) {
        return artistaRepository.findAll(pageable);
    }

    @Override
    public Page<Artista> findAllByName(String nome, Pageable pageable) {
        return artistaRepository.findAllByName(nome, pageable);
    }
}