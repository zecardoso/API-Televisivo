package com.apitelevisivo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apitelevisivo.model.RolePermissao;
import com.apitelevisivo.model.RolePermissaoId;

@Repository
public interface RolePermissaoRepository extends JpaRepository<RolePermissao, RolePermissaoId> {

}