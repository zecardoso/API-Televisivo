package com.apitelevisivo.web.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.apitelevisivo.model.Temporada;
import com.apitelevisivo.model.dto.converter.ConverterTemporada;
import com.apitelevisivo.model.dto.out.TemporadaOut;
import com.apitelevisivo.service.TemporadaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Temporada")
@RestController
@RequestMapping(value = "/api/temporada")
public class TemporadaRestController {

    // private static final String TEMPORADAS = "temporadas";

    @Autowired
    private TemporadaService temporadaService;

    @Autowired
    private ConverterTemporada converterTemporada;

    @ApiOperation("Buscar temporada por id")
	@ResponseBody
	@GetMapping("/alterar/{id}")
	public TemporadaOut buscarAlterar(@ApiParam(value = "ID de um temporada", example = "1") @PathVariable Long id) {
		Temporada temporada = temporadaService.getOne(id);
		TemporadaOut temporadaOut = converterTemporada.toModel(temporada);
		temporadaOut.add(linkTo(methodOn(TemporadaRestController.class).buscarAlterar(temporadaOut.getId())).withSelfRel());
		// temporadaOut.add(linkTo(methodOn(TemporadaRestController.class).listar()).withRel(USUARIOS));
		return temporadaOut;
	}

    // @ApiOperation("Adicionar temporada")
    // @ResponseBody
    // @PostMapping(value = "/adicionar")
    // @ResponseStatus(HttpStatus.OK)
    // public TemporadaOut registrar(@ApiParam(name = "Dados do temporada", value="Representação de um usuário" ) TemporadaIn in) {
    //     Temporada temporada = converterTemporada.converterInToTemporada(in);
	// 	return converterTemporada.converterTemporadaToOut(temporada);
    // }

    // @ApiOperation("Alterar temporada por id")
    // @ResponseBody
    // @PutMapping("/alterar/{id}")
    // public ResponseEntity<Temporada> alterar(@ApiParam(value = "ID de um temporada", example = "1") @PathVariable Long id, @RequestBody Temporada temporada) {
    //     try {
    //         Temporada temporada2 = temporadaService.findById(id);
    //         if (temporada2 != null) {
    //             BeanUtils.copyProperties(temporada, temporada2);
    //             temporada2 = temporadaService.update(temporada2);
    //             return ResponseEntity.ok(temporada2);
    //         }
    //     } catch (TemporadaNaoCadastradaException e) {
    //         throw new NegocioException("O temporada não existe no sistema");
    //     }
    //     return ResponseEntity.notFound().build();
    // }

    // @ApiOperation("Buscar temporada por id")
	// @ResponseBody
	// @GetMapping("/buscar/{id}")
	// public TemporadaOut buscar(@ApiParam(value = "ID de um temporada", example = "1") @PathVariable Long id) {
    //     Temporada temporada = temporadaService.getOne(id);
	// 	TemporadaOut temporadaOut = converterTemporada.converterTemporadaToOut(temporada);
	// 	temporadaOut.add(linkTo(methodOn(TemporadaRestController.class).buscar(temporadaOut.getId())).withSelfRel());
	// 	temporadaOut.add(linkTo(methodOn(TemporadaRestController.class).listar()).withRel(TEMPORADAS));
	// 	return temporadaOut;
    // }
    
    @ApiOperation("Remover usuário por id")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Temporada> remover(@PathVariable Long id) {
        temporadaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}