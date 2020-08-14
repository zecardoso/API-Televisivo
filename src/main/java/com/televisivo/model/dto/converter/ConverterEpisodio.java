package com.televisivo.model.dto.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.televisivo.model.Episodio;
import com.televisivo.model.dto.in.EpisodioIn;
import com.televisivo.model.dto.out.EpisodioOut;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConverterEpisodio {
    
    @Autowired
    private ModelMapper modelMapper;
    
    public Episodio converterInToEpisodio(EpisodioIn in) {
        return modelMapper.map(in, Episodio.class);
    }

    public EpisodioIn converterEpisodioToIn(Episodio episodio) {
        return modelMapper.map(episodio, EpisodioIn.class);
    }

    public Episodio converterOutToEpisodio(EpisodioOut out) {
        return modelMapper.map(out, Episodio.class);
    }

    public EpisodioOut converterEpisodioToOut(Episodio episodio) {
        return modelMapper.map(episodio, EpisodioOut.class);
    }

    public List<EpisodioOut> toCollectionsModel(List<Episodio> episodios) {
        return episodios.stream().map(episodio -> converterEpisodioToOut(episodio)).collect(Collectors.toList());
    }
}