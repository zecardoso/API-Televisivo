package com.televisivo.model.dto.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.televisivo.model.Elenco;
import com.televisivo.model.dto.in.ElencoIn;
import com.televisivo.model.dto.out.ElencoOut;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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