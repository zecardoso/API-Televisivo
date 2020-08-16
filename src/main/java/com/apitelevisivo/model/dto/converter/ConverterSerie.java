package com.apitelevisivo.model.dto.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apitelevisivo.model.Serie;
import com.apitelevisivo.model.dto.in.SerieIn;
import com.apitelevisivo.model.dto.out.SerieOut;

@Component
public class ConverterSerie {
    
    @Autowired
    private ModelMapper modelMapper;
    
    public Serie converterInToSerie(SerieIn in) {
        return modelMapper.map(in, Serie.class);
    }

    public SerieIn converterSerieToIn(Serie serie) {
        return modelMapper.map(serie, SerieIn.class);
    }

    public Serie converterOutToSerie(SerieOut out) {
        return modelMapper.map(out, Serie.class);
    }

    public SerieOut converterSerieToOut(Serie serie) {
        return modelMapper.map(serie, SerieOut.class);
    }

    public List<SerieOut> toCollectionsModel(List<Serie> series) {
        return series.stream().map(serie -> converterSerieToOut(serie)).collect(Collectors.toList());
    }
}