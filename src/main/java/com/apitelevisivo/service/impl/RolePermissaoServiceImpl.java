package com.apitelevisivo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apitelevisivo.model.RolePermissao;
import com.apitelevisivo.model.RolePermissaoId;
import com.apitelevisivo.repository.RolePermissaoRepository;
import com.apitelevisivo.service.RolePermissaoService;

@Service
@Transactional
public class RolePermissaoServiceImpl implements RolePermissaoService {

    @Autowired
    private RolePermissaoRepository rolePermissaoRepository;
    
    @Override
	@Transactional(readOnly = true)
    public List<RolePermissao> findAll() {
        return rolePermissaoRepository.findAll();
    }

    @Override
    public RolePermissao save(RolePermissao rolePermissao) {
        rolePermissaoRepository.flush();
        return rolePermissaoRepository.save(rolePermissao);
    }

    @Override
    public RolePermissao update(RolePermissao rolePermissao) {
        return rolePermissaoRepository.save(rolePermissao);
    }

    @Override
	@Transactional(readOnly = true)
    public RolePermissao getOne(RolePermissaoId id) {
        return rolePermissaoRepository.getOne(id);
    }

    @Override
    public RolePermissao findById(RolePermissaoId id) {
        return rolePermissaoRepository.getOne(id);
    }

    @Override
    public void deleteById(RolePermissaoId id) {
        rolePermissaoRepository.deleteById(id);
    }
}