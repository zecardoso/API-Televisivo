package com.televisivo.model.dto.in;

import java.util.Date;

import com.televisivo.model.Temporada;

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
    private Date publicacao;
    private Temporada temporada;
}