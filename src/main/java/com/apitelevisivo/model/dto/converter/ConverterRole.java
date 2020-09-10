package com.apitelevisivo.model.dto.converter;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.apitelevisivo.model.Role;
import com.apitelevisivo.model.dto.out.RoleOut;
import com.apitelevisivo.web.controller.RoleRestController;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ConverterRole extends RepresentationModelAssemblerSupport<Role, RoleOut> {
    
    @Autowired
    private ModelMapper modelMapper;
    
    public ConverterRole() {
        super(RoleRestController.class, RoleOut.class);
    }

    @Override
    public CollectionModel<RoleOut> toCollectionModel(Iterable<? extends Role> roles) {
        return super.toCollectionModel(roles).add(linkTo(RoleRestController.class).withSelfRel());
    }

    @Override
    public RoleOut toModel(Role role) {
        // RoleOut roleOut = createModelWithId(role.getId(), role)
        RoleOut roleOut = modelMapper.map(role, RoleOut.class);
        roleOut.add(linkTo(methodOn(RoleRestController.class).buscarAlterar(roleOut.getId())).withRel("role"));
        return roleOut;
    }
}