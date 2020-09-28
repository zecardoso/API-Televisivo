package com.apitelevisivo.web.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import javax.validation.Valid;

import com.apitelevisivo.model.Permissao;
import com.apitelevisivo.model.dto.converter.ConverterPermissao;
import com.apitelevisivo.model.dto.out.PermissaoOut;
import com.apitelevisivo.service.PermissaoService;
import com.apitelevisivo.service.exceptions.NegocioException;
import com.apitelevisivo.service.exceptions.PermissaoNaoCadastradaException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    private static final String NOME = "nome";

    @Autowired
    private PermissaoService permissaoService;

    @Autowired
    private ConverterPermissao converterPermissao;

    @Autowired
    private PagedResourcesAssembler<Permissao> pagedResourcesAssembler;

    @ApiOperation("Listar permissaos cors")
    @GetMapping(value = "/listar-todos")
    public List<Permissao> listarTodos() {
        return permissaoService.findAll();
    }

    @ApiOperation("Listar permissões")
    @GetMapping(value = "/listar")
    public PagedModel<PermissaoOut> listar(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "dir", defaultValue = "asc") String dir) {

        Pageable pageable = null;

        if (dir.equalsIgnoreCase("asc")) {
            pageable = PageRequest.of(page, limit, Sort.by(Direction.ASC, NOME));
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(Direction.DESC, NOME));
        }

        Page<Permissao> listaPermissao = permissaoService.findAll(pageable);
        return pagedResourcesAssembler.toModel(listaPermissao, converterPermissao);
    }

    @ApiOperation("Listar permissões por nome")
    @GetMapping(value = "/listar/{nome}")
    public PagedModel<PermissaoOut> listarByName(
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

        Page<Permissao> listaPermissao = permissaoService.findAllByName(nome, pageable);
        return pagedResourcesAssembler.toModel(listaPermissao, converterPermissao);
    }

    // @ApiOperation("Adicionar permissão")
    // @PostMapping(value = "/adicionar")
    // @ResponseStatus(code = HttpStatus.OK)
    // public PermissaoOut registrar(@ApiParam(name = "Dados da permissao", value = "Representação de uma permissao") Permissao permissao) {
    //     PermissaoOut permissaoOut = converterPermissao.toModel(permissao);
    //     return permissaoService.save(converterPermissao.toModel(permissao));
    // }

    @ApiOperation("Adicionar permissão")
    @PostMapping(value = "/adicionar")
    @ResponseStatus(HttpStatus.CREATED)
    public Permissao registrar(@ApiParam(name = "Dados da permissão", value = "Representação de uma permissão") @RequestBody @Valid Permissao permissao) {
        return permissaoService.save(permissao);
    }

    @RequestMapping(value = "/alterar/{id}", method = { RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<Permissao> alterar(@PathVariable Long id, @RequestBody Permissao permissao) {
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
	@GetMapping("/buscar/{id}")
	public PermissaoOut buscar(@ApiParam(value = "ID de uma permissão", example = "1") @PathVariable Long id) {
		Permissao permissao = permissaoService.getOne(id);
		PermissaoOut permissaoOut = converterPermissao.toModel(permissao);
		permissaoOut.add(linkTo(methodOn(PermissaoRestController.class).buscar(permissaoOut.getId())).withSelfRel());
		permissaoOut.add(linkTo(methodOn(PermissaoRestController.class).listar(0, 10, "asc")).withRel(PERMISSOES));
		return permissaoOut;
    }
    
    @ApiOperation("Remover permissão por id")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Permissao> remover(@PathVariable Long id) {
        permissaoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}