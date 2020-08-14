package com.televisivo.model.dto.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.televisivo.model.Categoria;
import com.televisivo.model.dto.in.CategoriaIn;
import com.televisivo.model.dto.out.CategoriaOut;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConverterCategoria {
    
    @Autowired
    private ModelMapper modelMapper;
    
    public Categoria converterInToCategoria(CategoriaIn in) {
        return modelMapper.map(in, Categoria.class);
    }

    public CategoriaIn converterCategoriaToIn(Categoria categoria) {
        return modelMapper.map(categoria, CategoriaIn.class);
    }

    public Categoria converterOutToCategoria(CategoriaOut out) {
        return modelMapper.map(out, Categoria.class);
    }

    public CategoriaOut converterCategoriaToOut(Categoria categoria) {
        return modelMapper.map(categoria, CategoriaOut.class);
    }

    public List<CategoriaOut> toCollectionsModel(List<Categoria> categorias) {
        return categorias.stream().map(categoria -> converterCategoriaToOut(categoria)).collect(Collectors.toList());
    }
}