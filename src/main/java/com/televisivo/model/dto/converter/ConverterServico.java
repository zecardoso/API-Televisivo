package com.televisivo.model.dto.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.televisivo.model.Servico;
import com.televisivo.model.dto.in.ServicoIn;
import com.televisivo.model.dto.out.ServicoOut;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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