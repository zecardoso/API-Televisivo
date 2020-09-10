package com.apitelevisivo.web.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.apitelevisivo.model.Serie;
import com.apitelevisivo.model.dto.converter.ConverterSerie;
import com.apitelevisivo.model.dto.out.SerieOut;
import com.apitelevisivo.service.JasperReportsService;
import com.apitelevisivo.service.SerieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Série")
@RestController
@RequestMapping(value = "/api/serie")
public class SerieRestController {

    // private static final String SERIES = "séries";
    private static final String NOME = "nome";

    @Autowired
    private SerieService serieService;

    @Autowired
    private ConverterSerie converterSerie;

    @Autowired
    private JasperReportsService jasperReportsService;
    
    @Autowired
    private PagedResourcesAssembler<Serie> pagedResourcesAssembler;

    @ApiOperation("Listar séries")
    @ResponseBody
    @GetMapping(value = "/listar")
    public PagedModel<SerieOut> listar(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "dir", defaultValue = "asc") String dir) {

        Pageable pageable = null;

        if (dir.equalsIgnoreCase("asc")) {
            pageable = PageRequest.of(page, limit, Sort.by(Direction.ASC, NOME));
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(Direction.DESC, NOME));
        }

        Page<Serie> listaSerie = serieService.findAll(pageable);
        return pagedResourcesAssembler.toModel(listaSerie, converterSerie);
    }

    @ApiOperation("Listar séries por nome")
    @ResponseBody
    @GetMapping(value = "/listar/{nome}")
    public PagedModel<SerieOut> listarByName(
            @PathVariable(NOME) String nome,    
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "dir", defaultValue = "asc") String dir) {

        Pageable pageable = null;

        if (dir.equalsIgnoreCase("asc")) {
            pageable = PageRequest.of(page, limit, Sort.by(Direction.ASC, NOME));
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(Direction.DESC, NOME));
        }

        Page<Serie> listaSerie = serieService.findAllByName(nome, pageable);
        return pagedResourcesAssembler.toModel(listaSerie, converterSerie);
    }

    @ApiOperation("Buscar série por id")
	@ResponseBody
	@GetMapping("/alterar/{id}")
	public SerieOut buscarAlterar(@ApiParam(value = "ID de um série", example = "1") @PathVariable Long id) {
		Serie serie = serieService.getOne(id);
		SerieOut serieOut = converterSerie.toModel(serie);
		serieOut.add(linkTo(methodOn(SerieRestController.class).buscarAlterar(serieOut.getId())).withSelfRel());
		// serieOut.add(linkTo(methodOn(SerieRestController.class).listar()).withRel(USUARIOS));
		return serieOut;
	}

    // @ApiOperation("Adicionar série")
    // @ResponseBody
    // @PostMapping(value = "/adicionar")
    // @ResponseStatus(HttpStatus.OK)
    // public SerieOut registrar(@ApiParam(name = "Dados do série", value="Representação de um usuário" ) SerieIn in) {
    //     Serie serie = converterSerie.converterInToSerie(in);
	// 	return converterSerie.converterSerieToOut(serie);
    // }

    // @ApiOperation("Alterar série por id")
    // @ResponseBody
    // @PutMapping("/alterar/{id}")
    // public ResponseEntity<Serie> alterar(@ApiParam(value = "ID de um série", example = "1") @PathVariable Long id, @RequestBody Serie serie) {
    //     try {
    //         Serie serie2 = serieService.findById(id);
    //         if (serie2 != null) {
    //             BeanUtils.copyProperties(serie, serie2);
    //             serie2 = serieService.update(serie2);
    //             return ResponseEntity.ok(serie2);
    //         }
    //     } catch (SerieNaoCadastradaException e) {
    //         throw new NegocioException("O serie não existe no sistema");
    //     }
    //     return ResponseEntity.notFound().build();
    // }

    // @ApiOperation("Buscar série por id")
	// @ResponseBody
	// @GetMapping("/buscar/{id}")
	// public SerieOut buscar(@ApiParam(value = "ID de um série", example = "1") @PathVariable Long id) {
    //     Serie serie = serieService.getOne(id);
	// 	SerieOut serieOut = converterSerie.converterSerieToOut(serie);
	// 	serieOut.add(linkTo(methodOn(SerieRestController.class).buscar(serieOut.getId())).withSelfRel());
	// 	serieOut.add(linkTo(methodOn(SerieRestController.class).listar()).withRel(SERIES));
	// 	return serieOut;
    // }
    
    @ApiOperation("Remover série por id")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Serie> remover(@PathVariable Long id) {
        serieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @ApiOperation("Relatório de séries em pdf")
    @GetMapping("/pdf")
    public ResponseEntity<byte[]> imprimeRelatorioPdf() {
    	byte[] relatorio = jasperReportsService.imprimeRelatorioNoNavegador("serie");
    	HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=serie.pdf");
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).headers(headers).body(relatorio);
    }
}