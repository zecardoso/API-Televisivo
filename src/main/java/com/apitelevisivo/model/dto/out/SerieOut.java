package com.apitelevisivo.model.dto.out;

import com.apitelevisivo.model.Servico;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SerieOut extends RepresentationModel<UsuarioOut> {
    
    private Long id;
    private String nome;
    private String enredo;
    private int ano;
    private int restricao;
    private int qtdTemporadas;
    private int qtdSeguidores;
    private Servico servicoId;
    private Servico servicoNome;

    public Long getServicoId() {
        return servicoId.getId();
    }

    public String getServicoNome() {
        return servicoNome.getNome();
    }
}