package com.apitelevisivo.model.dto.converter;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.apitelevisivo.model.Servico;
import com.apitelevisivo.model.dto.out.ServicoOut;
import com.apitelevisivo.web.controller.ServicoRestController;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ConverterServico extends RepresentationModelAssemblerSupport<Servico, ServicoOut> {
    
    @Autowired
    private ModelMapper modelMapper;
    
    public ConverterServico() {
        super(ServicoRestController.class, ServicoOut.class);
    }

    @Override
    public CollectionModel<ServicoOut> toCollectionModel(Iterable<? extends Servico> servicos) {
        return super.toCollectionModel(servicos).add(linkTo(ServicoRestController.class).withSelfRel());
    }

    @Override
    public ServicoOut toModel(Servico servico) {
        // ServicoOut servicoOut = createModelWithId(servico.getId(), servico)
        ServicoOut servicoOut = modelMapper.map(servico, ServicoOut.class);
        servicoOut.add(linkTo(methodOn(ServicoRestController.class).buscarAlterar(servicoOut.getId())).withRel("servico"));
        return servicoOut;
    }
}