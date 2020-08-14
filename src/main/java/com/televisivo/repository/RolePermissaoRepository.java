package com.televisivo.repository;

import com.televisivo.model.RolePermissao;
import com.televisivo.model.RolePermissaoId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissaoRepository extends JpaRepository<RolePermissao, RolePermissaoId> {

}