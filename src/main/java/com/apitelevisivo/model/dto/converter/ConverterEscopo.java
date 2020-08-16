package com.apitelevisivo.model.dto.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apitelevisivo.model.Escopo;
import com.apitelevisivo.model.dto.in.EscopoIn;
import com.apitelevisivo.model.dto.out.EscopoOut;

@Component
public class ConverterEscopo {
    
    @Autowired
    private ModelMapper modelMapper;
    
    public Escopo converterInToEscopo(EscopoIn in) {
        return modelMapper.map(in, Escopo.class);
    }

    public EscopoIn converterEscopoToIn(Escopo escopo) {
        return modelMapper.map(escopo, EscopoIn.class);
    }

    public Escopo converterOutToEscopo(EscopoOut out) {
        return modelMapper.map(out, Escopo.class);
    }

    public EscopoOut converterEscopoToOut(Escopo escopo) {
        return modelMapper.map(escopo, EscopoOut.class);
    }

    public List<EscopoOut> toCollectionsModel(List<Escopo> escopos) {
        return escopos.stream().map(escopo -> converterEscopoToOut(escopo)).collect(Collectors.toList());
    }
}