package com.apitelevisivo.service.impl;

import java.util.List;

import com.apitelevisivo.model.Categoria;
import com.apitelevisivo.repository.CategoriaRepository;
import com.apitelevisivo.service.CategoriaService;
import com.apitelevisivo.service.exceptions.CategoriaNaoCadastradaException;
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
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public Categoria update(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    @Transactional(readOnly = true)
    public Categoria getOne(Long id) {
        return categoriaRepository.getOne(id);
    }

    @Override
    public Categoria findById(Long id) {
		return categoriaRepository.findById(id).orElseThrow(()-> new CategoriaNaoCadastradaException(id));
    }

    @Override
    public void deleteById(Long id) {
		try {
			categoriaRepository.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("O categoria de código %d não pode ser removido!", id));
		} catch (EmptyResultDataAccessException e){
			throw new CategoriaNaoCadastradaException(String.format("A categoria com o código %d não foi encontrada!", id));
		}
    }

    @Override
    public Page<Categoria> findAll(Pageable pageable) {
        return categoriaRepository.findAll(pageable);
    }

    @Override
    public Page<Categoria> findAllByName(String nome, Pageable pageable) {
        return categoriaRepository.findAllByName(nome, pageable);
    }
}