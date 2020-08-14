package com.televisivo.model.dto.in;

import java.util.ArrayList;
import java.util.List;

import com.televisivo.model.Categoria;
import com.televisivo.model.Elenco;
import com.televisivo.model.Servico;
import com.televisivo.model.Temporada;

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
    private List<Temporada> temporadas = new ArrayList<>();
    private Servico servico;
    private List<Categoria> categorias = new ArrayList<>();
    private List<Elenco> elencos = new ArrayList<>();
}