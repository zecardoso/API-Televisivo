package com.televisivo.model.dto.in;

import java.util.ArrayList;
import java.util.List;

import com.televisivo.model.Episodio;
import com.televisivo.model.Serie;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TemporadaIn {
    
    private Long id;
    private int numero;
    private int ano;
    private Float avaliacao;
    private int qtdEpisodios;
    private List<Episodio> episodios = new ArrayList<>();
    private Serie serie;
}