package com.apitelevisivo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.apitelevisivo.model.Permission;
import com.apitelevisivo.repository.UsuarioRepository;
import com.apitelevisivo.security.UsuarioSistema;
import com.apitelevisivo.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public boolean hasPermission(Authentication usuarioLogado, Object permissao, Object escopo) {
        boolean toReturn = false;
        boolean truePermissao = false;
        boolean trueEscopo = false;

        UsuarioSistema usuarioSistema = (UsuarioSistema) usuarioLogado.getPrincipal();
        List<Permission> lista = this.findRolePermissaoByUsuarioId(usuarioSistema.getUsuario().getId());
        for (Permission permission : lista) {
            truePermissao = ((String) permissao).equalsIgnoreCase(permission.getPermissao());
            trueEscopo = ((String) escopo).equalsIgnoreCase(permission.getEscopo());
            if (truePermissao && trueEscopo) {
                toReturn = true;
                break;
            }
        }
        return toReturn;
    }

    @Override
    public List<Permission> findRolePermissaoByUsuarioId(Long id) {
        return usuarioRepository.findRolePermissaoByUsuarioId(id);
    }
}