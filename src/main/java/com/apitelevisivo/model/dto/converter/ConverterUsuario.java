package com.apitelevisivo.model.dto.converter;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.apitelevisivo.model.Usuario;
import com.apitelevisivo.model.dto.out.UsuarioOut;
import com.apitelevisivo.web.controller.UsuarioRestController;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ConverterUsuario extends RepresentationModelAssemblerSupport<Usuario, UsuarioOut> {
    
    @Autowired
    private ModelMapper modelMapper;
    
    public ConverterUsuario() {
        super(UsuarioRestController.class, UsuarioOut.class);
    }

    @Override
    public CollectionModel<UsuarioOut> toCollectionModel(Iterable<? extends Usuario> usuarios) {
        return super.toCollectionModel(usuarios).add(linkTo(UsuarioRestController.class).withSelfRel());
    }

    @Override
    public UsuarioOut toModel(Usuario usuario) {
        // UsuarioOut usuarioOut = createModelWithId(usuario.getId(), usuario)
        UsuarioOut usuarioOut = modelMapper.map(usuario, UsuarioOut.class);
        usuarioOut.add(linkTo(methodOn(UsuarioRestController.class).buscarAlterar(usuarioOut.getId())).withRel("usuario"));
        return usuarioOut;
    }
}