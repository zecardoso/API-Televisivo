package com.televisivo.model.dto.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.televisivo.model.Temporada;
import com.televisivo.model.dto.in.TemporadaIn;
import com.televisivo.model.dto.out.TemporadaOut;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConverterTemporada {
    
    @Autowired
    private ModelMapper modelMapper;
    
    public Temporada converterInToTemporada(TemporadaIn in) {
        return modelMapper.map(in, Temporada.class);
    }

    public TemporadaIn converterTemporadaToIn(Temporada temporada) {
        return modelMapper.map(temporada, TemporadaIn.class);
    }

    public Temporada converterOutToTemporada(TemporadaOut out) {
        return modelMapper.map(out, Temporada.class);
    }

    public TemporadaOut converterTemporadaToOut(Temporada temporada) {
        return modelMapper.map(temporada, TemporadaOut.class);
    }

    public List<TemporadaOut> toCollectionsModel(List<Temporada> temporadas) {
        return temporadas.stream().map(temporada -> converterTemporadaToOut(temporada)).collect(Collectors.toList());
    }
}