package com.apitelevisivo.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.apitelevisivo.model.Usuario;
import com.apitelevisivo.service.UsuarioService;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UsuarioService usuarioService;

    // @Autowired
    // private PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        request.getSession().setMaxInactiveInterval(60 * 60);
        UsuarioSistema usuarioLogado = (UsuarioSistema) authentication.getPrincipal();
        // if (Objects.isNull(usuarioLogado)) {
        //     persistentTokenBasedRememberMeServices.loginSuccess(request, response, authentication);
        // }
        updateLoginUsuario(usuarioLogado.getUsuario());
        redirectStrategy.sendRedirect(request, response, "/home");
    }

    private void updateLoginUsuario(Usuario usuario) {
        usuarioService.updateLoginUsuario(usuario);
    }
}