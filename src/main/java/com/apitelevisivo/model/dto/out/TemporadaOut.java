package com.apitelevisivo.model.dto.out;

import com.apitelevisivo.model.Serie;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TemporadaOut {
    
    private Long id;
    private int numero;
    private int ano;
    private Float avaliacao;
    private int qtdEpisodios;
    private Serie serie;

    public Long getSerie() {
        return serie.getId();
    }
}