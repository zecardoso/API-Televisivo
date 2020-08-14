package com.televisivo.service;

import com.televisivo.model.Role;

public interface RoleService extends GenericService<Role, Long> {

    Role findRole(String role);
	void saveUsuarioAuditoria(Role role, String operacao);
}