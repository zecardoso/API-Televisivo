package com.televisivo.model.dto.out;

import com.televisivo.model.Artista;

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