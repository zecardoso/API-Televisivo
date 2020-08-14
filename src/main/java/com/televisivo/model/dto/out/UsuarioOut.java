package com.televisivo.model.dto.out;

import com.televisivo.model.enumerate.Genero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioOut {
    
    private Long id;
    private String username;
    private String nome;
    private String sobrenome;
    private String email;
    private Genero genero;
}