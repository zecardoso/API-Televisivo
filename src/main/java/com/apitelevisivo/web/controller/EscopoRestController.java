package com.apitelevisivo.web.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import com.apitelevisivo.model.Escopo;
import com.apitelevisivo.model.dto.converter.ConverterEscopo;
import com.apitelevisivo.model.dto.in.EscopoIn;
import com.apitelevisivo.model.dto.out.EscopoOut;
import com.apitelevisivo.service.EscopoService;
import com.apitelevisivo.service.exceptions.EscopoNaoCadastradoException;
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

@Api(tags = "Escopo")
@RestController
@RequestMapping(value = "/api/escopo")
public class EscopoRestController {

    private static final String ESCOPOS = "escopos";

    @Autowired
    private EscopoService escopoService;

    @Autowired
    private ConverterEscopo converterEscopo;

    @ApiOperation("Listar escopos")
    @ResponseBody
    @GetMapping(value = "/listar")
    public CollectionModel<EscopoOut> listar() {
        List<Escopo> listaEscopo = escopoService.findAll();
        List<EscopoOut> listaEscopoOut = converterEscopo.toCollectionsModel(listaEscopo);
        listaEscopoOut.forEach(escopoOut -> {
			escopoOut.add(linkTo(methodOn(EscopoRestController.class).alterar(escopoOut.getId(), new Escopo())).withSelfRel());
		});
        CollectionModel<EscopoOut> escopoCollectionsModel = new CollectionModel<>(listaEscopoOut);
        escopoCollectionsModel.add(linkTo(methodOn(EscopoRestController.class).listar()).withRel(ESCOPOS));
        return escopoCollectionsModel;
    }

    @ApiOperation("Adicionar escopo")
    @ResponseBody
    @PostMapping(value = "/adicionar")
    @ResponseStatus(HttpStatus.OK)
    public EscopoOut registrar(@ApiParam(name = "Dados do Usuário", value="Representação de um usuário" ) EscopoIn in) {
        Escopo escopo = converterEscopo.converterInToEscopo(in);
		return converterEscopo.converterEscopoToOut(escopo);
    }

    @ApiOperation("Alterar escopo por id")
    @ResponseBody
    @PutMapping("/alterar/{id}")
    public ResponseEntity<Escopo> alterar(@ApiParam(value = "ID de um Usuário", example = "1") @PathVariable Long id, @RequestBody Escopo escopo) {
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

    @ApiOperation("Buscar escopo por id")
	@ResponseBody
	@GetMapping("/buscar/{id}")
	public EscopoOut buscar(@ApiParam(value = "ID de um Usuário", example = "1") @PathVariable Long id) {
        Escopo escopo = escopoService.getOne(id);
		EscopoOut escopoOut = converterEscopo.converterEscopoToOut(escopo);
		escopoOut.add(linkTo(methodOn(EscopoRestController.class).buscar(escopoOut.getId())).withSelfRel());
		escopoOut.add(linkTo(methodOn(EscopoRestController.class).listar()).withRel(ESCOPOS));
		return escopoOut;
    }
    
    @ApiOperation("Remover escopo por id")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Escopo> remover(@PathVariable Long id) {
        escopoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}