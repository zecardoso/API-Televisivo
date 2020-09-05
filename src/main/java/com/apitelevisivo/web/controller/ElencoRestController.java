package com.apitelevisivo.web.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import com.apitelevisivo.model.Elenco;
import com.apitelevisivo.model.dto.converter.ConverterElenco;
import com.apitelevisivo.model.dto.in.ElencoIn;
import com.apitelevisivo.model.dto.out.ElencoOut;
import com.apitelevisivo.service.ElencoService;
import com.apitelevisivo.service.exceptions.ElencoNaoCadastradoException;
import com.apitelevisivo.service.exceptions.NegocioException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Elenco")
@RestController
@RequestMapping(value = "/api/elenco")
public class ElencoRestController {

    private static final String ELENCOS = "elencos";

    @Autowired
    private ElencoService elencoService;

    @Autowired
    private ConverterElenco converterElenco;

    @ApiOperation("Listar elencos")
    @ResponseBody
    @GetMapping(value = "/listar")
    public CollectionModel<ElencoOut> listar() {
        List<Elenco> listaElenco = elencoService.findAll();
        List<ElencoOut> listaElencoOut = converterElenco.toCollectionsModel(listaElenco);
        listaElencoOut.forEach(elencoOut -> {
			elencoOut.add(linkTo(methodOn(ElencoRestController.class).alterar(elencoOut.getId(), new Elenco())).withSelfRel());
		});
        CollectionModel<ElencoOut> elencoCollectionsModel = new CollectionModel<>(listaElencoOut);
        elencoCollectionsModel.add(linkTo(methodOn(ElencoRestController.class).listar()).withRel(ELENCOS));
        return elencoCollectionsModel;
    }

    @ApiOperation("Adicionar elenco")
    @ResponseBody
    @PostMapping(value = "/adicionar")
    @ResponseStatus(HttpStatus.OK)
    public ElencoOut registrar(@ApiParam(name = "Dados do Usuário", value="Representação de um elenco" ) ElencoIn in) {
        Elenco elenco = converterElenco.converterInToElenco(in);
		return converterElenco.converterElencoToOut(elenco);
    }

    @ApiOperation("Alterar elenco por id")
    @ResponseBody
    @PutMapping("/alterar/{id}")
    public ResponseEntity<Elenco> alterar(@ApiParam(value = "ID de um Usuário", example = "1") @PathVariable Long id, @RequestBody Elenco elenco) {
        try {
            Elenco elenco2 = elencoService.findById(id);
            if (elenco2 != null) {
                BeanUtils.copyProperties(elenco, elenco2);
                elenco2 = elencoService.update(elenco2);
                return ResponseEntity.ok(elenco2);
            }
        } catch (ElencoNaoCadastradoException e) {
            throw new NegocioException("O elenco não existe no sistema");
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation("Buscar elenco por id")
	@ResponseBody
	@GetMapping("/buscar/{id}")
	public ElencoOut buscar(@ApiParam(value = "ID de um Usuário", example = "1") @PathVariable Long id) {
        Elenco elenco = elencoService.getOne(id);
		ElencoOut elencoOut = converterElenco.converterElencoToOut(elenco);
		elencoOut.add(linkTo(methodOn(ElencoRestController.class).buscar(elencoOut.getId())).withSelfRel());
		elencoOut.add(linkTo(methodOn(ElencoRestController.class).listar()).withRel(ELENCOS));
		return elencoOut;
    }
    
    @ApiOperation("Remover elenco por id")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Elenco> remover(@PathVariable Long id) {
        elencoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}