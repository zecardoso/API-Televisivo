package com.apitelevisivo.model.dto.converter;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.apitelevisivo.model.Episodio;
import com.apitelevisivo.model.dto.out.EpisodioOut;
import com.apitelevisivo.web.controller.EpisodioRestController;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ConverterEpisodio extends RepresentationModelAssemblerSupport<Episodio, EpisodioOut> {
    
    @Autowired
    private ModelMapper modelMapper;
    
    public ConverterEpisodio() {
        super(EpisodioRestController.class, EpisodioOut.class);
    }

    @Override
    public CollectionModel<EpisodioOut> toCollectionModel(Iterable<? extends Episodio> episodios) {
        return super.toCollectionModel(episodios).add(linkTo(EpisodioRestController.class).withSelfRel());
    }

    @Override
    public EpisodioOut toModel(Episodio episodio) {
        // EpisodioOut episodioOut = createModelWithId(episodio.getId(), episodio)
        EpisodioOut episodioOut = modelMapper.map(episodio, EpisodioOut.class);
        episodioOut.add(linkTo(methodOn(EpisodioRestController.class).buscarAlterar(episodioOut.getId())).withRel("episodio"));
        return episodioOut;
    }
}