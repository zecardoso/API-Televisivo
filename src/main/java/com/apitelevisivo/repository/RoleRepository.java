package com.apitelevisivo.repository;

import java.util.List;

import com.apitelevisivo.model.Role;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT r FROM Role r LEFT JOIN r.usuarios")
    List<Role> findAllRoles();
    
    @Query("SELECT u FROM Role u WHERE u.nome LIKE LOWER(CONCAT('%', :nome, '%'))")
	Page<Role> findAllByName(@Param("nome") String nome, Pageable pageable);
}