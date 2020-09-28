package com.apitelevisivo.web.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import javax.validation.Valid;

import com.apitelevisivo.model.Role;
import com.apitelevisivo.model.dto.converter.ConverterRole;
import com.apitelevisivo.model.dto.out.RoleOut;
import com.apitelevisivo.service.RoleService;
import com.apitelevisivo.service.exceptions.NegocioException;
import com.apitelevisivo.service.exceptions.RoleNaoCadastradaException;

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

@Api(tags = "Role")
@RestController
@RequestMapping(value = "/api/role")
public class RoleRestController {

    private static final String ROLES = "roles";
    private static final String NOME = "nome";

    @Autowired
    private RoleService roleService;

    @Autowired
    private ConverterRole converterRole;

    @Autowired
    private PagedResourcesAssembler<Role> pagedResourcesAssembler;

    @ApiOperation("Listar roles cors")
    @GetMapping(value = "/listar-todos")
    public List<Role> listarTodos() {
        return roleService.findAll();
    }

    @ApiOperation("Listar roles")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/listar")
    public PagedModel<RoleOut> listar(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "dir", defaultValue = "asc") String dir) {

        Pageable pageable = null;

        if (dir.equalsIgnoreCase("asc")) {
            pageable = PageRequest.of(page, limit, Sort.by(Direction.ASC, NOME));
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(Direction.DESC, NOME));
        }

        Page<Role> listaRole = roleService.findAll(pageable);
        return pagedResourcesAssembler.toModel(listaRole, converterRole);
    }

    @ApiOperation("Listar roles por nome")
    @GetMapping(value = "/listar/{nome}")
    public PagedModel<RoleOut> listarByName(
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

        Page<Role> listaRole = roleService.findAllByName(nome, pageable);
        return pagedResourcesAssembler.toModel(listaRole, converterRole);
    }

    // @ApiOperation("Adicionar role")
    // @PostMapping(value = "/adicionar")
    // @ResponseStatus(code = HttpStatus.CREATED)
    // public RoleOut registrar(@ApiParam(name = "Dados da role", value = "Representação de uma role") Role role) {
    //     RoleOut roleOut = converterRole.toModel(role);
    //     return roleService.save(converterRole.toModel(role));
    // }

    @ApiOperation("Adicionar role")
    @PostMapping(value = "/adicionar")
    @ResponseStatus(HttpStatus.CREATED)
    public Role registrar(@ApiParam(name = "Dados da role", value = "Representação de uma role") @RequestBody @Valid Role role) {
        return roleService.save(role);
    }

    @RequestMapping(value = "/alterar/{id}", method = { RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<Role> alterar(@PathVariable Long id, @RequestBody Role role) {
        try {
            Role role2 = roleService.findById(id);
            if (role2 != null) {
                BeanUtils.copyProperties(role, role2);
                role2 = roleService.update(role2);
                return ResponseEntity.ok(role2);
            }
        } catch (RoleNaoCadastradaException e) {
            throw new NegocioException("O role não existe no sistema");
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation("Buscar role por id")
	@GetMapping("/buscar/{id}")
	public RoleOut buscar(@ApiParam(value = "ID de uma role", example = "1") @PathVariable Long id) {
		Role role = roleService.getOne(id);
		RoleOut roleOut = converterRole.toModel(role);
		roleOut.add(linkTo(methodOn(RoleRestController.class).buscar(roleOut.getId())).withSelfRel());
		roleOut.add(linkTo(methodOn(RoleRestController.class).listar(0, 10, "asc")).withRel(ROLES));
		return roleOut;
    }
    
    @ApiOperation("Remover role por id")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Role> remover(@PathVariable Long id) {
        roleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}