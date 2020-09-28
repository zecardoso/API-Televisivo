package com.apitelevisivo.model.dto.converter;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.apitelevisivo.model.Permissao;
import com.apitelevisivo.model.dto.out.PermissaoOut;
import com.apitelevisivo.web.controller.PermissaoRestController;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ConverterPermissao extends RepresentationModelAssemblerSupport<Permissao, PermissaoOut> {
    
    @Autowired
    private ModelMapper modelMapper;
    
    public ConverterPermissao() {
        super(PermissaoRestController.class, PermissaoOut.class);
    }

    @Override
    public CollectionModel<PermissaoOut> toCollectionModel(Iterable<? extends Permissao> permissoes) {
        return super.toCollectionModel(permissoes).add(linkTo(PermissaoRestController.class).withSelfRel());
    }

    @Override
    public PermissaoOut toModel(Permissao permissao) {
        // PermissaoOut permissaoOut = createModelWithId(permissao.getId(), permissao)
        PermissaoOut permissaoOut = modelMapper.map(permissao, PermissaoOut.class);
        permissaoOut.add(linkTo(methodOn(PermissaoRestController.class).buscar(permissaoOut.getId())).withRel("permissao"));
        return permissaoOut;
    }
}