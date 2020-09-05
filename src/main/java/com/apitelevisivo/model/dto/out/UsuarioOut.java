package com.apitelevisivo.model.dto.out;

import com.apitelevisivo.model.enumerate.Genero;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioOut extends RepresentationModel<UsuarioOut> {
    
    private Long id;
    private String username;
    private String nome;
    private String sobrenome;
    private String email;
    private Genero genero;
}