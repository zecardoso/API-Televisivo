package com.televisivo.model.dto.out;

import com.televisivo.model.Temporada;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EpisodioOut {
    
    private Long id;
    private int numero;
    private String nome;
    private String enredo;
    private Float avaliacao;
    private int duracao;
    private Temporada temporada;

    public Long getTemporada() {
        return temporada.getId();
    }

    public Long getSerie() {
        return temporada.getSerie().getId();
    }
}