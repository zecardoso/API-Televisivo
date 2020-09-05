package com.apitelevisivo.web.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import com.apitelevisivo.model.Permissao;
import com.apitelevisivo.model.dto.converter.ConverterPermissao;
import com.apitelevisivo.model.dto.in.PermissaoIn;
import com.apitelevisivo.model.dto.out.PermissaoOut;
import com.apitelevisivo.service.PermissaoService;
import com.apitelevisivo.service.exceptions.NegocioException;
import com.apitelevisivo.service.exceptions.PermissaoNaoCadastradaException;

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

@Api(tags = "Permissão")
@RestController
@RequestMapping(value = "/api/permissao")
public class PermissaoRestController {

    private static final String PERMISSOES = "permissões";

    @Autowired
    private PermissaoService permissaoService;

    @Autowired
    private ConverterPermissao converterPermissao;

    @ApiOperation("Listar permissões")
    @ResponseBody
    @GetMapping(value = "/listar")
    public CollectionModel<PermissaoOut> listar() {
        List<Permissao> listaPermissao = permissaoService.findAll();
        List<PermissaoOut> listaPermissaoOut = converterPermissao.toCollectionsModel(listaPermissao);
        listaPermissaoOut.forEach(permissaoOut -> {
			permissaoOut.add(linkTo(methodOn(PermissaoRestController.class).alterar(permissaoOut.getId(), new Permissao())).withSelfRel());
		});
        CollectionModel<PermissaoOut> permissaoCollectionsModel = new CollectionModel<>(listaPermissaoOut);
        permissaoCollectionsModel.add(linkTo(methodOn(PermissaoRestController.class).listar()).withRel(PERMISSOES));
        return permissaoCollectionsModel;
    }

    @ApiOperation("Adicionar permissão")
    @ResponseBody
    @PostMapping(value = "/adicionar")
    @ResponseStatus(HttpStatus.OK)
    public PermissaoOut registrar(@ApiParam(name = "Dados do Usuário", value="Representação de um usuário" ) PermissaoIn in) {
        Permissao permissao = converterPermissao.converterInToPermissao(in);
		return converterPermissao.converterPermissaoToOut(permissao);
    }

    @ApiOperation("Alterar permissão por id")
    @ResponseBody
    @PutMapping("/alterar/{id}")
    public ResponseEntity<Permissao> alterar(@ApiParam(value = "ID de um Usuário", example = "1") @PathVariable Long id, @RequestBody Permissao permissao) {
        try {
            Permissao permissao2 = permissaoService.findById(id);
            if (permissao2 != null) {
                BeanUtils.copyProperties(permissao, permissao2);
                permissao2 = permissaoService.update(permissao2);
                return ResponseEntity.ok(permissao2);
            }
        } catch (PermissaoNaoCadastradaException e) {
            throw new NegocioException("O permissao não existe no sistema");
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation("Buscar permissão por id")
	@ResponseBody
	@GetMapping("/buscar/{id}")
	public PermissaoOut buscar(@ApiParam(value = "ID de um Usuário", example = "1") @PathVariable Long id) {
        Permissao permissao = permissaoService.getOne(id);
		PermissaoOut permissaoOut = converterPermissao.converterPermissaoToOut(permissao);
		permissaoOut.add(linkTo(methodOn(PermissaoRestController.class).buscar(permissaoOut.getId())).withSelfRel());
		permissaoOut.add(linkTo(methodOn(PermissaoRestController.class).listar()).withRel(PERMISSOES));
		return permissaoOut;
    }
    
    @ApiOperation("Remover permissão por id")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Permissao> remover(@PathVariable Long id) {
        permissaoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}