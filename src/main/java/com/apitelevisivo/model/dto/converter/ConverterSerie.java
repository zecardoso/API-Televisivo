package com.apitelevisivo.model.dto.converter;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.apitelevisivo.model.Serie;
import com.apitelevisivo.model.dto.out.SerieOut;
import com.apitelevisivo.web.controller.SerieRestController;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ConverterSerie extends RepresentationModelAssemblerSupport<Serie, SerieOut> {
    
    @Autowired
    private ModelMapper modelMapper;
    
    public ConverterSerie() {
        super(SerieRestController.class, SerieOut.class);
    }

    @Override
    public CollectionModel<SerieOut> toCollectionModel(Iterable<? extends Serie> series) {
        return super.toCollectionModel(series).add(linkTo(SerieRestController.class).withSelfRel());
    }

    @Override
    public SerieOut toModel(Serie serie) {
        // SerieOut serieOut = createModelWithId(serie.getId(), serie)
        SerieOut serieOut = modelMapper.map(serie, SerieOut.class);
        serieOut.add(linkTo(methodOn(SerieRestController.class).buscarAlterar(serieOut.getId())).withRel("serie"));
        return serieOut;
    }
}