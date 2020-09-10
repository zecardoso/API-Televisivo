package com.apitelevisivo.web.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.apitelevisivo.model.Escopo;
import com.apitelevisivo.model.dto.converter.ConverterEscopo;
import com.apitelevisivo.model.dto.out.EscopoOut;
import com.apitelevisivo.service.EscopoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
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

@Api(tags = "Escopo")
@RestController
@RequestMapping(value = "/api/escopo")
public class EscopoRestController {

    // private static final String ESCOPOS = "escopos";
    private static final String NOME = "nome";

    @Autowired
    private EscopoService escopoService;

    @Autowired
    private ConverterEscopo converterEscopo;

    @Autowired
    private PagedResourcesAssembler<Escopo> pagedResourcesAssembler;

    @ApiOperation("Listar escopos")
    @ResponseBody
    @GetMapping(value = "/listar")
    public PagedModel<EscopoOut> listar(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "dir", defaultValue = "asc") String dir) {

        Pageable pageable = null;

        if (dir.equalsIgnoreCase("asc")) {
            pageable = PageRequest.of(page, limit, Sort.by(Direction.ASC, NOME));
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(Direction.DESC, NOME));
        }

        Page<Escopo> listaEscopo = escopoService.findAll(pageable);
        return pagedResourcesAssembler.toModel(listaEscopo, converterEscopo);
    }

    @ApiOperation("Listar escopos por nome")
    @ResponseBody
    @GetMapping(value = "/listar/{nome}")
    public PagedModel<EscopoOut> listarByName(
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

        Page<Escopo> listaEscopo = escopoService.findAllByName(nome, pageable);
        return pagedResourcesAssembler.toModel(listaEscopo, converterEscopo);
    }

    @ApiOperation("Buscar escopo por id")
	@ResponseBody
	@GetMapping("/alterar/{id}")
	public EscopoOut buscarAlterar(@ApiParam(value = "ID de um escopo", example = "1") @PathVariable Long id) {
		Escopo escopo = escopoService.getOne(id);
		EscopoOut escopoOut = converterEscopo.toModel(escopo);
		escopoOut.add(linkTo(methodOn(EscopoRestController.class).buscarAlterar(escopoOut.getId())).withSelfRel());
		// escopoOut.add(linkTo(methodOn(EscopoRestController.class).listar()).withRel(USUARIOS));
		return escopoOut;
	}

    // @ApiOperation("Adicionar escopo")
    // @ResponseBody
    // @PostMapping(value = "/adicionar")
    // @ResponseStatus(HttpStatus.OK)
    // public EscopoOut registrar(@ApiParam(name = "Dados do escopo", value="Representação de um usuário" ) EscopoIn in) {
    //     Escopo escopo = converterEscopo.converterInToEscopo(in);
	// 	return converterEscopo.converterEscopoToOut(escopo);
    // }

    // @ApiOperation("Alterar escopo por id")
    // @ResponseBody
    // @PutMapping("/alterar/{id}")
    // public ResponseEntity<Escopo> alterar(@ApiParam(value = "ID de um escopo", example = "1") @PathVariable Long id, @RequestBody Escopo escopo) {
    //     try {
    //         Escopo escopo2 = escopoService.findById(id);
    //         if (escopo2 != null) {
    //             BeanUtils.copyProperties(escopo, escopo2);
    //             escopo2 = escopoService.update(escopo2);
    //             return ResponseEntity.ok(escopo2);
    //         }
    //     } catch (EscopoNaoCadastradoException e) {
    //         throw new NegocioException("O escopo não existe no sistema");
    //     }
    //     return ResponseEntity.notFound().build();
    // }

    // @ApiOperation("Buscar escopo por id")
	// @ResponseBody
	// @GetMapping("/buscar/{id}")
	// public EscopoOut buscar(@ApiParam(value = "ID de um escopo", example = "1") @PathVariable Long id) {
    //     Escopo escopo = escopoService.getOne(id);
	// 	EscopoOut escopoOut = converterEscopo.converterEscopoToOut(escopo);
	// 	escopoOut.add(linkTo(methodOn(EscopoRestController.class).buscar(escopoOut.getId())).withSelfRel());
	// 	escopoOut.add(linkTo(methodOn(EscopoRestController.class).listar()).withRel(ESCOPOS));
	// 	return escopoOut;
    // }
    
    @ApiOperation("Remover escopo por id")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Escopo> remover(@PathVariable Long id) {
        escopoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}