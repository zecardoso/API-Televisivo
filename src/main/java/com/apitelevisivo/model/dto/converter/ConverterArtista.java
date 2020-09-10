package com.apitelevisivo.model.dto.converter;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.apitelevisivo.model.Artista;
import com.apitelevisivo.model.dto.out.ArtistaOut;
import com.apitelevisivo.web.controller.ArtistaRestController;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ConverterArtista extends RepresentationModelAssemblerSupport<Artista, ArtistaOut> {
    
    @Autowired
    private ModelMapper modelMapper;
    
    public ConverterArtista() {
        super(ArtistaRestController.class, ArtistaOut.class);
    }

    @Override
    public CollectionModel<ArtistaOut> toCollectionModel(Iterable<? extends Artista> artistas) {
        return super.toCollectionModel(artistas).add(linkTo(ArtistaRestController.class).withSelfRel());
    }

    @Override
    public ArtistaOut toModel(Artista artista) {
        // ArtistaOut artistaOut = createModelWithId(artista.getId(), artista)
        ArtistaOut artistaOut = modelMapper.map(artista, ArtistaOut.class);
        artistaOut.add(linkTo(methodOn(ArtistaRestController.class).buscarAlterar(artistaOut.getId())).withRel("artista"));
        return artistaOut;
    }
}