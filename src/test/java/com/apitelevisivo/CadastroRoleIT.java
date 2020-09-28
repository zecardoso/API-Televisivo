package com.apitelevisivo;

import static org.assertj.core.api.Assertions.assertThat;

import com.apitelevisivo.model.Role;
import com.apitelevisivo.service.RoleService;
import com.apitelevisivo.service.exceptions.RoleNaoCadastradaException;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CadastroRoleIT {

    @Autowired
    private RoleService roleService;

    @Test
    public void cadastrarSucesso() {
        Role role = new Role();
        role.setNome("GERENTE");
        role = roleService.save(role);
        assertThat(role).isNotNull();
        assertThat(role.getId()).isNotNull();
    }

    @Test(expected = ConstraintViolationException.class)
    public void cadastrarSemNome() {
        Role role = new Role();
        role.setNome(null);
        role = roleService.save(role);
    }

    @Test(expected = RoleNaoCadastradaException.class)
    public void naoEncontrada() {
        roleService.deleteById(100L);
    }
}