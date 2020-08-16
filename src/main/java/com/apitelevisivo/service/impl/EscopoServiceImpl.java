package com.apitelevisivo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apitelevisivo.model.Escopo;
import com.apitelevisivo.repository.EscopoRepository;
import com.apitelevisivo.service.EscopoService;
import com.apitelevisivo.service.exceptions.EntidadeEmUsoException;
import com.apitelevisivo.service.exceptions.EscopoNaoCadastradoException;

@Service
@Transactional
public class EscopoServiceImpl implements EscopoService {

    @Autowired
    private EscopoRepository escopoRepository;
    
    @Override
	@Transactional(readOnly = true)
    public List<Escopo> findAll() {
        return escopoRepository.findAll();
    }

    @Override
    public Escopo save(Escopo escopo) {
        return escopoRepository.save(escopo);
    }

    @Override
    public Escopo update(Escopo escopo) {
        return escopoRepository.save(escopo);
    }

    @Override
	@Transactional(readOnly = true)
    public Escopo getOne(Long id) {
        return escopoRepository.getOne(id);
    }

    @Override
    public Escopo findById(Long id) {
		return escopoRepository.findById(id).orElseThrow(()-> new EscopoNaoCadastradoException(id));
    }

    @Override
    public void deleteById(Long id) {
        try {
			escopoRepository.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("O escopo de código %d não pode ser removido!", id));
		} catch (EmptyResultDataAccessException e){
			throw new EscopoNaoCadastradoException(String.format("O escopo com o código %d não foi encontrado!", id));
		}
    }
}