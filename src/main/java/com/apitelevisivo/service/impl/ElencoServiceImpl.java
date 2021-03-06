package com.apitelevisivo.service.impl;

import java.util.List;

import com.apitelevisivo.model.Elenco;
import com.apitelevisivo.repository.ElencoRepository;
import com.apitelevisivo.service.ElencoService;
import com.apitelevisivo.service.exceptions.ElencoNaoCadastradoException;
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
public class ElencoServiceImpl implements ElencoService {

    @Autowired
    private ElencoRepository elencoRepository;
    
    @Override
	@Transactional(readOnly = true)
    public List<Elenco> findAll() {
        return elencoRepository.findAll();
    }

    @Override
    public Elenco save(Elenco elenco) {
        return elencoRepository.save(elenco);
    }

    @Override
    public Elenco update(Elenco elenco) {
        return elencoRepository.save(elenco);
    }

    @Override
	@Transactional(readOnly = true)
    public Elenco getOne(Long id) {
        return elencoRepository.getOne(id);
    }

    @Override
    public Elenco findById(Long id) {
		return elencoRepository.findById(id).orElseThrow(()-> new ElencoNaoCadastradoException(id));
    }

    @Override
    public void deleteById(Long id) {
		try {
			elencoRepository.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("O elenco de código %d não pode ser removido!", id));
		} catch (EmptyResultDataAccessException e){
			throw new ElencoNaoCadastradoException(String.format("O elenco com o código %d não foi encontrado!", id));
		}
    }

    @Override
    public Page<Elenco> findAll(Pageable pageable) {
        return elencoRepository.findAll(pageable);
    }
}