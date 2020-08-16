package com.apitelevisivo.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.apitelevisivo.model.Escopo;
import com.apitelevisivo.model.dto.converter.ConverterEscopo;
import com.apitelevisivo.model.dto.out.EscopoOut;
import com.apitelevisivo.service.EscopoService;
import com.apitelevisivo.service.exceptions.EscopoNaoCadastradoException;
import com.apitelevisivo.service.exceptions.NegocioException;

@RestController
@RequestMapping(value = "/api/escopo")
public class EscopoRestController {

    @Autowired
    private EscopoService escopoService;

    @Autowired
    private ConverterEscopo converterEscopo;

    @ResponseBody
    @GetMapping(value = "/listar")
    public List<EscopoOut> listar() {
        return converterEscopo.toCollectionsModel(escopoService.findAll());
    }

    @PostMapping(value = "/adicionar")
    @ResponseStatus(HttpStatus.OK)
    public Escopo registrar(@RequestBody @Valid Escopo escopo) {
        return escopoService.save(escopo);
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<Escopo> alterar(@PathVariable Long id, @RequestBody Escopo escopo) {
        try {
            Escopo escopo2 = escopoService.findById(id);
            if (escopo2 != null) {
                BeanUtils.copyProperties(escopo, escopo2);
                escopo2 = escopoService.update(escopo2);
                return ResponseEntity.ok(escopo2);
            }
        } catch (EscopoNaoCadastradoException e) {
            throw new NegocioException("O escopo não existe no sistema");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        escopoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping(value = "/buscar/{id}")
    public ResponseEntity<Escopo> buscar(@PathVariable Long id) {
        Escopo escopo = escopoService.findById(id);
        if (escopo != null) {
            return ResponseEntity.ok(escopo);
        }
        return ResponseEntity.notFound().build();
    }
}