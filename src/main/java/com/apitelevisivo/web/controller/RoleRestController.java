package com.apitelevisivo.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.apitelevisivo.model.Role;
import com.apitelevisivo.model.dto.converter.ConverterRole;
import com.apitelevisivo.model.dto.out.RoleOut;
import com.apitelevisivo.service.RoleService;
import com.apitelevisivo.service.exceptions.NegocioException;
import com.apitelevisivo.service.exceptions.RoleNaoCadastradaException;

@RestController
@RequestMapping(value = "/api/role")
public class RoleRestController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ConverterRole converterRole;

    @ResponseBody
    @GetMapping(value = "/listar")
    public List<RoleOut> listar() {
        return converterRole.toCollectionsModel(roleService.findAll());
    }

    @PostMapping(value = "/adicionar")
    @ResponseStatus(HttpStatus.OK)
    public Role registrar(@RequestBody @Valid Role role) {
        return roleService.save(role);
    }

    @PutMapping("/alterar/{id}")
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

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        roleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping(value = "/buscar/{id}")
    public ResponseEntity<Role> buscar(@PathVariable Long id) {
        Role role = roleService.findById(id);
        if (role != null) {
            return ResponseEntity.ok(role);
        }
        return ResponseEntity.notFound().build();
    }
}