package com.apitelevisivo.service.impl;

import java.util.List;

import com.apitelevisivo.model.Permissao;
import com.apitelevisivo.repository.PermissaoRepository;
import com.apitelevisivo.service.PermissaoService;
import com.apitelevisivo.service.exceptions.EntidadeEmUsoException;
import com.apitelevisivo.service.exceptions.PermissaoNaoCadastradaException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PermissaoServiceImpl implements PermissaoService {

    @Autowired
    private PermissaoRepository permissaoRepository;
    
    @Override
	@Transactional(readOnly = true)
    public List<Permissao> findAll() {
        return permissaoRepository.findAll();
    }

    @Override
    public Permissao save(Permissao permissao) {
        return permissaoRepository.save(permissao);
    }

    @Override
    public Permissao update(Permissao permissao) {
        return permissaoRepository.save(permissao);
    }

    @Override
	@Transactional(readOnly = true)
    public Permissao getOne(Long id) {
        return permissaoRepository.getOne(id);
    }

    @Override
    public Permissao findById(Long id) {
		return permissaoRepository.findById(id).orElseThrow(()-> new PermissaoNaoCadastradaException(id));
    }

    @Override
    public void deleteById(Long id) {
        try {
            permissaoRepository.deleteById(id);
        } catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("A Permissao de código %d não pode ser removida!", id));
		} catch (EmptyResultDataAccessException e){
			throw new PermissaoNaoCadastradaException(String.format("A permissao com o código %d não foi encontrada!", id));
		}
    }

    @Override
    public Page<Permissao> findAll(Pageable pageable) {
        return permissaoRepository.findAll(pageable);
    }

    @Override
    public Page<Permissao> findAllByName(String nome, Pageable pageable) {
        return permissaoRepository.findAllByName(nome, pageable);
    }
}