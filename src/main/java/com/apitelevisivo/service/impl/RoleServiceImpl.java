package com.apitelevisivo.service.impl;

import java.util.Date;
import java.util.List;

import com.apitelevisivo.config.TelevisivoConfig;
import com.apitelevisivo.model.Role;
import com.apitelevisivo.model.UsuarioAuditoria;
import com.apitelevisivo.repository.RoleRepository;
import com.apitelevisivo.repository.UsuarioAuditoriaRepository;
import com.apitelevisivo.service.RoleService;
import com.apitelevisivo.service.exceptions.EntidadeEmUsoException;
import com.apitelevisivo.service.exceptions.RoleNaoCadastradaException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private UsuarioAuditoriaRepository usuarioAuditoriaRepository;
    
    @Override
	@Transactional(readOnly = true)
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role save(Role role) {
        role = roleRepository.save(role);
        saveUsuarioAuditoria(role, TelevisivoConfig.INCLUSAO);
        return role;
    }

    @Override
    public Role update(Role role) {
        role = roleRepository.save(role);
        saveUsuarioAuditoria(role, TelevisivoConfig.ALTERACAO);
        return role;
    }

    @Override
	@Transactional(readOnly = true)
    public Role getOne(Long id) {
        return roleRepository.getOne(id);
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new RoleNaoCadastradaException(id));
    }

    @Override
    public void deleteById(Long id) {
        try {
            saveUsuarioAuditoria(getOne(id), TelevisivoConfig.EXCLUSAO);
            roleRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("A Role de código %d não pode ser removida.", id));
        } catch (EmptyResultDataAccessException e) {
            throw new RoleNaoCadastradaException(String.format("A role com o código %d não foi encontrada.", id));
        }
    }

    @Override
    public Role findRole(String role) {
        return null;
    }

    @Override
	@Transactional
	public void saveUsuarioAuditoria(Role role, String operacao) {
		UsuarioAuditoria usuarioAuditoria = new UsuarioAuditoria();
		usuarioAuditoria.getAuditoria().setDataOperacao(new Date());
		usuarioAuditoria.getAuditoria().setUsuario(TelevisivoConfig.pegarUsuario());
		usuarioAuditoria.getAuditoria().setTipoOperacao(operacao);
		usuarioAuditoria.setRole(role);
        usuarioAuditoriaRepository.save(usuarioAuditoria); 		
	}

    @Override
    public Page<Role> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    @Override
    public Page<Role> findAllByName(String nome, Pageable pageable) {
        return roleRepository.findAllByName(nome, pageable);
    }
}