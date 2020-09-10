package com.apitelevisivo.model.dto.converter;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.apitelevisivo.model.Categoria;
import com.apitelevisivo.model.dto.out.CategoriaOut;
import com.apitelevisivo.web.controller.CategoriaRestController;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ConverterCategoria extends RepresentationModelAssemblerSupport<Categoria, CategoriaOut> {
    
    @Autowired
    private ModelMapper modelMapper;
    
    public ConverterCategoria() {
        super(CategoriaRestController.class, CategoriaOut.class);
    }

    @Override
    public CollectionModel<CategoriaOut> toCollectionModel(Iterable<? extends Categoria> categorias) {
        return super.toCollectionModel(categorias).add(linkTo(CategoriaRestController.class).withSelfRel());
    }

    @Override
    public CategoriaOut toModel(Categoria categoria) {
        // CategoriaOut categoriaOut = createModelWithId(categoria.getId(), categoria)
        CategoriaOut categoriaOut = modelMapper.map(categoria, CategoriaOut.class);
        categoriaOut.add(linkTo(methodOn(CategoriaRestController.class).buscarAlterar(categoriaOut.getId())).withRel("categoria"));
        return categoriaOut;
    }
}