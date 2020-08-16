package com.apitelevisivo.model.dto.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apitelevisivo.model.Role;
import com.apitelevisivo.model.dto.in.RoleIn;
import com.apitelevisivo.model.dto.out.RoleOut;

@Component
public class ConverterRole {
    
    @Autowired
    private ModelMapper modelMapper;
    
    public Role converterInToRole(RoleIn in) {
        return modelMapper.map(in, Role.class);
    }

    public RoleIn converterRoleToIn(Role role) {
        return modelMapper.map(role, RoleIn.class);
    }

    public Role converterOutToRole(RoleOut out) {
        return modelMapper.map(out, Role.class);
    }

    public RoleOut converterRoleToOut(Role role) {
        return modelMapper.map(role, RoleOut.class);
    }

    public List<RoleOut> toCollectionsModel(List<Role> roles) {
        return roles.stream().map(role -> converterRoleToOut(role)).collect(Collectors.toList());
    }
}