package com.apitelevisivo.model.dto.out;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicoOut extends RepresentationModel<UsuarioOut> {
    
    private Long id;
    private String nome;
}