package com.apitelevisivo.model.dto.in;

import com.apitelevisivo.model.enumerate.Genero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioIn {
    
    private Long id;
    private String username;
    private String nome;
    private String sobrenome;
    private String email;
    private Genero genero;
}