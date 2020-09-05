package com.apitelevisivo.web.controller;

import java.util.Optional;

import com.apitelevisivo.model.Login;
import com.apitelevisivo.model.TokenResponse;
import com.apitelevisivo.model.Usuario;
import com.apitelevisivo.security.jwt.JwtTokenProvider;
import com.apitelevisivo.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api(tags = "Login")
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginRestController {
    
    @Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping("/login")
	public ResponseEntity<TokenResponse> login(@RequestBody Login login){
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));
			Optional<Usuario> usuario = usuarioService.loginUsuarioByEmail(login.getEmail());
			TokenResponse tokenResponse = new TokenResponse();
			tokenResponse.setEmail(login.getEmail());
			tokenResponse.setToken(tokenProvider.createToken(login.getEmail(), usuario.get().getRoles()));
			return ResponseEntity.ok(tokenResponse);
		}catch(UsernameNotFoundException e) {
		   throw new UsernameNotFoundException("Usuário não cadastrado!");
	    }catch(BadCredentialsException e) {
	    	throw new BadCredentialsException("Senha inválida!");
	    }
	}
}