package com.apitelevisivo.model.dto.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apitelevisivo.model.Servico;
import com.apitelevisivo.model.dto.in.ServicoIn;
import com.apitelevisivo.model.dto.out.ServicoOut;

@Component
public class ConverterServico {
    
    @Autowired
    private ModelMapper modelMapper;
    
    public Servico converterInToServico(ServicoIn in) {
        return modelMapper.map(in, Servico.class);
    }

    public ServicoIn converterServicoToIn(Servico servico) {
        return modelMapper.map(servico, ServicoIn.class);
    }

    public Servico converterOutToServico(ServicoOut out) {
        return modelMapper.map(out, Servico.class);
    }

    public ServicoOut converterServicoToOut(Servico servico) {
        return modelMapper.map(servico, ServicoOut.class);
    }

    public List<ServicoOut> toCollectionsModel(List<Servico> servicos) {
        return servicos.stream().map(servico -> converterServicoToOut(servico)).collect(Collectors.toList());
    }
}