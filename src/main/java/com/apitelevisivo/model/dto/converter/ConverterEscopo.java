package com.apitelevisivo.model.dto.converter;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.apitelevisivo.model.Escopo;
import com.apitelevisivo.model.dto.out.EscopoOut;
import com.apitelevisivo.web.controller.EscopoRestController;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ConverterEscopo extends RepresentationModelAssemblerSupport<Escopo, EscopoOut> {
    
    @Autowired
    private ModelMapper modelMapper;
    
    public ConverterEscopo() {
        super(EscopoRestController.class, EscopoOut.class);
    }

    @Override
    public CollectionModel<EscopoOut> toCollectionModel(Iterable<? extends Escopo> escopos) {
        return super.toCollectionModel(escopos).add(linkTo(EscopoRestController.class).withSelfRel());
    }

    @Override
    public EscopoOut toModel(Escopo escopo) {
        // EscopoOut escopoOut = createModelWithId(escopo.getId(), escopo)
        EscopoOut escopoOut = modelMapper.map(escopo, EscopoOut.class);
        escopoOut.add(linkTo(methodOn(EscopoRestController.class).buscarAlterar(escopoOut.getId())).withRel("escopo"));
        return escopoOut;
    }
}