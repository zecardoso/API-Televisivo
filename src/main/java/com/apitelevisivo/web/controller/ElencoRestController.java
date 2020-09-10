package com.apitelevisivo.web.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.apitelevisivo.model.Elenco;
import com.apitelevisivo.model.dto.converter.ConverterElenco;
import com.apitelevisivo.model.dto.out.ElencoOut;
import com.apitelevisivo.service.ElencoService;

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

@Api(tags = "Elenco")
@RestController
@RequestMapping(value = "/api/elenco")
public class ElencoRestController {

    // private static final String ELENCOS = "elencos";
    private static final String NOME = "nome";

    @Autowired
    private ElencoService elencoService;

    @Autowired
    private ConverterElenco converterElenco;

    @Autowired
    private PagedResourcesAssembler<Elenco> pagedResourcesAssembler;

    @ApiOperation("Listar elencos")
    @ResponseBody
    @GetMapping(value = "/listar")
    public PagedModel<ElencoOut> listar(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "dir", defaultValue = "asc") String dir) {

        Pageable pageable = null;

        if (dir.equalsIgnoreCase("asc")) {
            pageable = PageRequest.of(page, limit, Sort.by(Direction.ASC, NOME));
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(Direction.DESC, NOME));
        }

        Page<Elenco> listaElenco = elencoService.findAll(pageable);
        return pagedResourcesAssembler.toModel(listaElenco, converterElenco);
    }
    
    @ApiOperation("Buscar elenco por id")
	@ResponseBody
	@GetMapping("/alterar/{id}")
	public ElencoOut buscarAlterar(@ApiParam(value = "ID de um elenco", example = "1") @PathVariable Long id) {
		Elenco elenco = elencoService.getOne(id);
		ElencoOut elencoOut = converterElenco.toModel(elenco);
		elencoOut.add(linkTo(methodOn(ElencoRestController.class).buscarAlterar(elencoOut.getId())).withSelfRel());
		// elencoOut.add(linkTo(methodOn(ElencoRestController.class).listar()).withRel(USUARIOS));
		return elencoOut;
	}

    // @ApiOperation("Adicionar elenco")
    // @ResponseBody
    // @PostMapping(value = "/adicionar")
    // @ResponseStatus(HttpStatus.OK)
    // public ElencoOut registrar(@ApiParam(name = "Dados do elenco", value="Representação de um elenco" ) ElencoIn in) {
    //     Elenco elenco = converterElenco.converterInToElenco(in);
	// 	return converterElenco.converterElencoToOut(elenco);
    // }

    // @ApiOperation("Alterar elenco por id")
    // @ResponseBody
    // @PutMapping("/alterar/{id}")
    // public ResponseEntity<Elenco> alterar(@ApiParam(value = "ID de um elenco", example = "1") @PathVariable Long id, @RequestBody Elenco elenco) {
    //     try {
    //         Elenco elenco2 = elencoService.findById(id);
    //         if (elenco2 != null) {
    //             BeanUtils.copyProperties(elenco, elenco2);
    //             elenco2 = elencoService.update(elenco2);
    //             return ResponseEntity.ok(elenco2);
    //         }
    //     } catch (ElencoNaoCadastradoException e) {
    //         throw new NegocioException("O elenco não existe no sistema");
    //     }
    //     return ResponseEntity.notFound().build();
    // }

    // @ApiOperation("Buscar elenco por id")
	// @ResponseBody
	// @GetMapping("/buscar/{id}")
	// public ElencoOut buscar(@ApiParam(value = "ID de um elenco", example = "1") @PathVariable Long id) {
    //     Elenco elenco = elencoService.getOne(id);
	// 	ElencoOut elencoOut = converterElenco.converterElencoToOut(elenco);
	// 	elencoOut.add(linkTo(methodOn(ElencoRestController.class).buscar(elencoOut.getId())).withSelfRel());
	// 	elencoOut.add(linkTo(methodOn(ElencoRestController.class).listar()).withRel(ELENCOS));
	// 	return elencoOut;
    // }
    
    @ApiOperation("Remover elenco por id")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Elenco> remover(@PathVariable Long id) {
        elencoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}