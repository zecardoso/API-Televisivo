package com.apitelevisivo.model.dto.converter;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.apitelevisivo.model.Temporada;
import com.apitelevisivo.model.dto.out.TemporadaOut;
import com.apitelevisivo.web.controller.TemporadaRestController;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ConverterTemporada extends RepresentationModelAssemblerSupport<Temporada, TemporadaOut> {
    
    @Autowired
    private ModelMapper modelMapper;
    
    public ConverterTemporada() {
        super(TemporadaRestController.class, TemporadaOut.class);
    }

    @Override
    public CollectionModel<TemporadaOut> toCollectionModel(Iterable<? extends Temporada> temporadas) {
        return super.toCollectionModel(temporadas).add(linkTo(TemporadaRestController.class).withSelfRel());
    }

    @Override
    public TemporadaOut toModel(Temporada temporada) {
        // TemporadaOut temporadaOut = createModelWithId(temporada.getId(), temporada)
        TemporadaOut temporadaOut = modelMapper.map(temporada, TemporadaOut.class);
        temporadaOut.add(linkTo(methodOn(TemporadaRestController.class).buscarAlterar(temporadaOut.getId())).withRel("temporada"));
        return temporadaOut;
    }
}