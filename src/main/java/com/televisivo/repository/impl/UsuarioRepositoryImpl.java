package com.televisivo.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.televisivo.model.Permission;
import com.televisivo.model.Usuario;
import com.televisivo.repository.query.UsuarioQuery;

public class UsuarioRepositoryImpl implements UsuarioQuery {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Usuario> buscarAtivoPorEmail(String email) {
        boolean ativo = Boolean.TRUE;
		TypedQuery<Usuario> query = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.email =:email AND u.ativo =:ativo", Usuario.class);
		return query.setParameter("email", email).setParameter("ativo", ativo).setMaxResults(1).getResultList().stream().findFirst();
    }

    @Override
    public Optional<Usuario> findUsuarioByEmail(String email) {
		TypedQuery<Usuario> query = entityManager.createQuery("SELECT u FROM Usuario u LEFT JOIN FETCH u.roles WHERE u.email =:email", Usuario.class);
		return query.setParameter("email", email).setMaxResults(1).getResultList().stream().findFirst();
    }

    @Override
    public Optional<Usuario> loginUsuarioByEmail(String email) {
		return findUsuarioByEmail(email);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Permission> findRolePermissaoByUsuarioId(Long id) {
        List<Permission> lista = new ArrayList<>();
        Query query = entityManager.createNativeQuery("select role.nome r_nome, permissao.nome p_nome, escopo.nome e_nome from usuario inner join usuario_role on usuario_role.usuario_id = usuario.usuario_id inner join role on role.role_id = usuario_role.role_id inner join role_permissao on role_permissao.role_id = role.role_id inner join permissao on role_permissao.permissao_id = permissao.permissao_id inner join escopo on role_permissao.escopo_id = escopo.escopo_id where usuario.usuario_id =:id").setParameter("id", id);
        List<Object[]> mylista = query.getResultList();
        for (int i = 0; i < mylista.size(); i++) {
            Permission permission = new Permission();
            permission.setRole(mylista.get(i)[0].toString());
            permission.setPermissao(mylista.get(i)[1].toString());
            permission.setEscopo(mylista.get(i)[2].toString());
            lista.add(permission);
        }
        return lista;
    }
}