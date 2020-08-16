package com.apitelevisivo.model.dto.out;

import com.apitelevisivo.model.Artista;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ElencoOut {
    
    private Long id;
    private String nome;
    private boolean diretor;
    private boolean ator;
    private String personagem;
    private Artista artista;
}