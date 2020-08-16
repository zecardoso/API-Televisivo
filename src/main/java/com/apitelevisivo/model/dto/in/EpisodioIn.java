package com.apitelevisivo.model.dto.in;

import com.apitelevisivo.model.Temporada;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EpisodioIn {
    
    private Long id;
    private int numero;
    private String nome;
    private String enredo;
    private Float avaliacao;
    private int duracao;
    // private Date publicacao;
    private Temporada temporada;
}