package com.apitelevisivo.service;

import com.apitelevisivo.model.Role;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService extends GenericService<Role, Long> {

    Role findRole(String role);
    void saveUsuarioAuditoria(Role role, String operacao);
    
	Page<Role> findAll(Pageable pageable);
	Page<Role> findAllByName(String nome, Pageable pageable);
}