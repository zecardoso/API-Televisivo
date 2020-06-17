package com.televisivo.model.dto.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.televisivo.model.Usuario;
import com.televisivo.model.dto.in.UsuarioIn;
import com.televisivo.model.dto.out.UsuarioOut;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConverterUsuario {
    
    @Autowired
    private ModelMapper modelMapper;
    
    public Usuario converterInToUsuario(UsuarioIn in) {
        return modelMapper.map(in, Usuario.class);
    }

    public UsuarioIn converterUsuarioToIn(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioIn.class);
    }

    public Usuario converterOutToUsuario(UsuarioOut out) {
        return modelMapper.map(out, Usuario.class);
    }

    public UsuarioOut converterUsuarioToOut(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioOut.class);
    }

    public List<UsuarioOut> toCollectionsModel(List<Usuario> usuarios) {
        return usuarios.stream().map(usuario -> converterUsuarioToOut(usuario)).collect(Collectors.toList());
    }
}