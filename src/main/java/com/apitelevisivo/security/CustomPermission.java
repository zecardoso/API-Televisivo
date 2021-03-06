package com.apitelevisivo.security;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.apitelevisivo.service.PermissionService;

@Component
public class CustomPermission implements PermissionEvaluator {

    private PermissionService permissionService;

    public CustomPermission(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Override
    public boolean hasPermission(Authentication usuarioLogado, Object permissao, Object escopo) {
        if (usuarioLogado == null || !usuarioLogado.isAuthenticated()) {
            return false;
        } else {
            return permissionService.hasPermission(usuarioLogado, permissao, escopo);
        }
    }

    @Override
    public boolean hasPermission(Authentication usuarioLogado, Serializable id, String permissao, Object escopo) {
        return false;
    }
}