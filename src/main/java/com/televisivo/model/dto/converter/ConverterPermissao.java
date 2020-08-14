package com.televisivo.model.dto.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.televisivo.model.Permissao;
import com.televisivo.model.dto.in.PermissaoIn;
import com.televisivo.model.dto.out.PermissaoOut;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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