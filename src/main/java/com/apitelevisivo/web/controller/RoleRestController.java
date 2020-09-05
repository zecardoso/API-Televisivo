package com.apitelevisivo.web.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import com.apitelevisivo.model.Role;
import com.apitelevisivo.model.dto.converter.ConverterRole;
import com.apitelevisivo.model.dto.in.RoleIn;
import com.apitelevisivo.model.dto.out.RoleOut;
import com.apitelevisivo.service.RoleService;
import com.apitelevisivo.service.exceptions.NegocioException;
import com.apitelevisivo.service.exceptions.RoleNaoCadastradaException;

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

@Api(tags = "Role")
@RestController
@RequestMapping(value = "/api/role")
public class RoleRestController {

    private static final String ROLES = "roles";

    @Autowired
    private RoleService roleService;

    @Autowired
    private ConverterRole converterRole;

    @ApiOperation("Listar roles")
    @ResponseBody
    @GetMapping(value = "/listar")
    public CollectionModel<RoleOut> listar() {
        List<Role> listaRole = roleService.findAll();
        List<RoleOut> listaRoleOut = converterRole.toCollectionsModel(listaRole);
        listaRoleOut.forEach(roleOut -> {
			roleOut.add(linkTo(methodOn(RoleRestController.class).alterar(roleOut.getId(), new Role())).withSelfRel());
		});
        CollectionModel<RoleOut> roleCollectionsModel = new CollectionModel<>(listaRoleOut);
        roleCollectionsModel.add(linkTo(methodOn(RoleRestController.class).listar()).withRel(ROLES));
        return roleCollectionsModel;
    }

    @ApiOperation("Adicionar role")
    @ResponseBody
    @PostMapping(value = "/adicionar")
    @ResponseStatus(HttpStatus.OK)
    public RoleOut registrar(@ApiParam(name = "Dados do Usuário", value="Representação de um usuário" ) RoleIn in) {
        Role role = converterRole.converterInToRole(in);
		return converterRole.converterRoleToOut(role);
    }

    @ApiOperation("Alterar role por id")
    @ResponseBody
    @PutMapping("/alterar/{id}")
    public ResponseEntity<Role> alterar(@ApiParam(value = "ID de um Usuário", example = "1") @PathVariable Long id, @RequestBody Role role) {
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
	@ResponseBody
	@GetMapping("/buscar/{id}")
	public RoleOut buscar(@ApiParam(value = "ID de um Usuário", example = "1") @PathVariable Long id) {
        Role role = roleService.getOne(id);
		RoleOut roleOut = converterRole.converterRoleToOut(role);
		roleOut.add(linkTo(methodOn(RoleRestController.class).buscar(roleOut.getId())).withSelfRel());
		roleOut.add(linkTo(methodOn(RoleRestController.class).listar()).withRel(ROLES));
		return roleOut;
    }
    
    @ApiOperation("Remover role por id")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Role> remover(@PathVariable Long id) {
        roleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}