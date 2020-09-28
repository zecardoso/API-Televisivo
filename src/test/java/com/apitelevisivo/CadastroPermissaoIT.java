package com.apitelevisivo;

import static org.assertj.core.api.Assertions.assertThat;

import com.apitelevisivo.model.Permissao;
import com.apitelevisivo.service.PermissaoService;
import com.apitelevisivo.service.exceptions.PermissaoNaoCadastradaException;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CadastroPermissaoIT {

    @Autowired
    private PermissaoService roleService;

    @Test
    public void cadastrarSucesso() {
        Permissao role = new Permissao();
        role.setNome("GERENTE");
        role = roleService.save(role);
        assertThat(role).isNotNull();
        assertThat(role.getId()).isNotNull();
    }

    @Test(expected = ConstraintViolationException.class)
    public void cadastrarSemNome() {
        Permissao role = new Permissao();
        role.setNome(null);
        role = roleService.save(role);
    }

    @Test(expected = PermissaoNaoCadastradaException.class)
    public void naoEncontrada() {
        roleService.deleteById(100L);
    }
}