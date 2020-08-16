package com.apitelevisivo.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apitelevisivo.model.Usuario;
import com.apitelevisivo.repository.UsuarioRepository;
import com.apitelevisivo.service.UsuarioService;
import com.apitelevisivo.service.exceptions.EmailCadastradoException;
import com.apitelevisivo.service.exceptions.SenhaError;
import com.apitelevisivo.service.exceptions.UsuarioNaoCadastradoException;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}
    
    @Override
	@Transactional(readOnly = true)
	// @PreAuthorize("hasPermission('ADMINISTRADOR','LEITURA')")
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
	// @PreAuthorize("hasPermission('ADMINISTRADOR','INSERIR')")
    public Usuario save(Usuario usuario) {
        Optional<Usuario> usuarioCadastrado = findUsuarioByEmail(usuario.getEmail());
        if (usuarioCadastrado .isPresent() && !usuarioCadastrado.get().equals(usuario)) {
            throw new EmailCadastradoException(String.format("O E-mail %s já está cadastrado no sistema ", usuario.getEmail()));
        }
        if (!usuario.getPassword().equals(usuario.getContraSenha())) {
            throw new SenhaError("Senha incorreta.");
        }
        usuario.setPassword(encodePassword(usuario.getPassword()));
        usuario.setAtivo(Boolean.TRUE);
        return usuarioRepository.save(usuario);
    }

    @Override
	// @PreAuthorize("hasPermission('ADMINISTRADOR','ATUALIZAR')")
    public Usuario update(Usuario usuario) {
        return this.save(usuario);
    }

    @Override
	@Transactional(readOnly = true)
	// @PreAuthorize("hasPermission('ADMINISTRADOR','LEITURA')")
    public Usuario getOne(Long id) {
		return usuarioRepository.getOne(id);
    }

    @Override
	// @PreAuthorize("hasPermission('ADMINISTRADOR','LEITURA')")
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNaoCadastradoException(id));
    }

    @Override
	// @PreAuthorize("hasPermission('ADMINISTRADOR','EXCLUIR')")
    public void deleteById(Long id) {
        try {
            usuarioRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new UsuarioNaoCadastradoException(String.format("O usuario com o código %d não foi encontrado!", id));
        }
    }

    @Override
    public Optional<Usuario> findUsuarioByEmail(String email) {
        return usuarioRepository.findUsuarioByEmail(email);
    }

    @Override
    public Optional<Usuario> loginUsuarioByEmail(String email) {
        return usuarioRepository.loginUsuarioByEmail(email);
    }

    @Override
    public void updateLoginUsuario(Usuario usuario) {
        usuario.setLastLogin(new Date());
        usuario.setFailedLogin(0);
        usuarioRepository.save(usuario);
    }

    @Override
    public void updateFaileAccess(Usuario usuario) {
        Integer totalAcesso = usuario.getFailedLogin() + 1;
        usuario.setFailedLogin(totalAcesso);
        usuarioRepository.save(usuario);
    }

    @Override
    public void blockedUsuario(Usuario usuario) {
        usuario.setAtivo(Boolean.FALSE);
        usuarioRepository.save(usuario);
    }
}