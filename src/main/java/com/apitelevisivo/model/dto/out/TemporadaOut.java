package com.apitelevisivo.model.dto.out;

import com.apitelevisivo.model.Serie;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TemporadaOut extends RepresentationModel<UsuarioOut> {
    
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