package com.apitelevisivo.model.dto.converter;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.apitelevisivo.model.Elenco;
import com.apitelevisivo.model.dto.out.ElencoOut;
import com.apitelevisivo.web.controller.ElencoRestController;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ConverterElenco extends RepresentationModelAssemblerSupport<Elenco, ElencoOut> {
    
    @Autowired
    private ModelMapper modelMapper;
    
    public ConverterElenco() {
        super(ElencoRestController.class, ElencoOut.class);
    }

    @Override
    public CollectionModel<ElencoOut> toCollectionModel(Iterable<? extends Elenco> elencos) {
        return super.toCollectionModel(elencos).add(linkTo(ElencoRestController.class).withSelfRel());
    }

    @Override
    public ElencoOut toModel(Elenco elenco) {
        // ElencoOut elencoOut = createModelWithId(elenco.getId(), elenco)
        ElencoOut elencoOut = modelMapper.map(elenco, ElencoOut.class);
        elencoOut.add(linkTo(methodOn(ElencoRestController.class).buscarAlterar(elencoOut.getId())).withRel("elenco"));
        return elencoOut;
    }
}