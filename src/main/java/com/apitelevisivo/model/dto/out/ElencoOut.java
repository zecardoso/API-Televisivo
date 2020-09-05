package com.apitelevisivo.model.dto.out;

import com.apitelevisivo.model.Artista;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ElencoOut extends RepresentationModel<UsuarioOut> {
    
    private Long id;
    private String nome;
    private boolean diretor;
    private boolean ator;
    private String personagem;
    private Artista artista;
}