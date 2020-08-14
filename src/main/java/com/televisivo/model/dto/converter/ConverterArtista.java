package com.televisivo.model.dto.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.televisivo.model.Artista;
import com.televisivo.model.dto.in.ArtistaIn;
import com.televisivo.model.dto.out.ArtistaOut;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConverterArtista {
    
    @Autowired
    private ModelMapper modelMapper;
    
    public Artista converterInToArtista(ArtistaIn in) {
        return modelMapper.map(in, Artista.class);
    }

    public ArtistaIn converterArtistaToIn(Artista artista) {
        return modelMapper.map(artista, ArtistaIn.class);
    }

    public Artista converterOutToArtista(ArtistaOut out) {
        return modelMapper.map(out, Artista.class);
    }

    public ArtistaOut converterArtistaToOut(Artista artista) {
        return modelMapper.map(artista, ArtistaOut.class);
    }

    public List<ArtistaOut> toCollectionsModel(List<Artista> artistas) {
        return artistas.stream().map(artista -> converterArtistaToOut(artista)).collect(Collectors.toList());
    }
}