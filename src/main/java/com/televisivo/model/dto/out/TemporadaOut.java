package com.televisivo.model.dto.out;

import java.util.ArrayList;
import java.util.List;

import com.televisivo.model.Episodio;
import com.televisivo.model.Serie;

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
    private List<Episodio> episodios = new ArrayList<>();
    private Serie serie;
}