package com.apitelevisivo.service.impl;

import java.util.List;

import com.apitelevisivo.model.Servico;
import com.apitelevisivo.repository.ServicoRepository;
import com.apitelevisivo.service.ServicoService;
import com.apitelevisivo.service.exceptions.EntidadeEmUsoException;
import com.apitelevisivo.service.exceptions.ServicoNaoCadastradoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicoServiceImpl implements ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;
    
    @Override
	@Transactional(readOnly = true)
    public List<Servico> findAll() {
        return servicoRepository.findAll();
    }

    @Override
    public Servico save(Servico servico) {
        return servicoRepository.save(servico);
    }

    @Override
    public Servico update(Servico servico) {
        return servicoRepository.save(servico);
    }

    @Override
	@Transactional(readOnly = true)
    public Servico getOne(Long id) {
        return servicoRepository.getOne(id);
    }

    @Override
    public Servico findById(Long id) {
        return servicoRepository.findById(id).orElseThrow(() -> new ServicoNaoCadastradoException(id));
    }

    @Override
    public void deleteById(Long id) {
		try {
			servicoRepository.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("O serviço de código %d não pode ser removida!", id));
		} catch (EmptyResultDataAccessException e){
			throw new ServicoNaoCadastradoException(String.format("O serviço com o código %d não foi encontrada!", id));
		}
    }

    @Override
    public Page<Servico> findAll(Pageable pageable) {
        return servicoRepository.findAll(pageable);
    }

    @Override
    public Page<Servico> findAllByName(String nome, Pageable pageable) {
        return servicoRepository.findAllByName(nome, pageable);
    }
}