package com.apitelevisivo.model.dto.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apitelevisivo.model.Elenco;
import com.apitelevisivo.model.dto.in.ElencoIn;
import com.apitelevisivo.model.dto.out.ElencoOut;

@Component
public class ConverterElenco {
    
    @Autowired
    private ModelMapper modelMapper;
    
    public Elenco converterInToElenco(ElencoIn in) {
        return modelMapper.map(in, Elenco.class);
    }

    public ElencoIn converterElencoToIn(Elenco elenco) {
        return modelMapper.map(elenco, ElencoIn.class);
    }

    public Elenco converterOutToElenco(ElencoOut out) {
        return modelMapper.map(out, Elenco.class);
    }

    public ElencoOut converterElencoToOut(Elenco elenco) {
        return modelMapper.map(elenco, ElencoOut.class);
    }

    public List<ElencoOut> toCollectionsModel(List<Elenco> elencos) {
        return elencos.stream().map(elenco -> converterElencoToOut(elenco)).collect(Collectors.toList());
    }
}