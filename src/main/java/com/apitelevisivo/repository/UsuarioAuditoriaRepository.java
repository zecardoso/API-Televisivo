package com.apitelevisivo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apitelevisivo.model.UsuarioAuditoria;

@Repository
public interface UsuarioAuditoriaRepository extends JpaRepository<UsuarioAuditoria, Long> {
    
}