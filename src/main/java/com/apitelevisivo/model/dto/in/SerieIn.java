package com.apitelevisivo.model.dto.in;

import java.util.ArrayList;
import java.util.List;

import com.apitelevisivo.model.Categoria;
import com.apitelevisivo.model.Elenco;
import com.apitelevisivo.model.Servico;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SerieIn {
    
    private Long id;
    private String nome;
    private String enredo;
    private int ano;
    private int restricao;
    private int qtdTemporadas;
    private int qtdSeguidores;
    private Servico servico;
    private List<Categoria> categorias = new ArrayList<>();
    private List<Elenco> elencos = new ArrayList<>();
}