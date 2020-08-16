package com.apitelevisivo.model.dto.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apitelevisivo.model.Permissao;
import com.apitelevisivo.model.dto.in.PermissaoIn;
import com.apitelevisivo.model.dto.out.PermissaoOut;

@Component
public class ConverterPermissao {
    
    @Autowired
    private ModelMapper modelMapper;
    
    public Permissao converterInToPermissao(PermissaoIn in) {
        return modelMapper.map(in, Permissao.class);
    }

    public PermissaoIn converterPermissaoToIn(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoIn.class);
    }

    public Permissao converterOutToPermissao(PermissaoOut out) {
        return modelMapper.map(out, Permissao.class);
    }

    public PermissaoOut converterPermissaoToOut(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoOut.class);
    }

    public List<PermissaoOut> toCollectionsModel(List<Permissao> permissaos) {
        return permissaos.stream().map(permissao -> converterPermissaoToOut(permissao)).collect(Collectors.toList());
    }
}