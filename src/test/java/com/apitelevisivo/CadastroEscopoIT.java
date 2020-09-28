package com.apitelevisivo;

import static org.assertj.core.api.Assertions.assertThat;

import com.apitelevisivo.model.Escopo;
import com.apitelevisivo.service.EscopoService;
import com.apitelevisivo.service.exceptions.EscopoNaoCadastradoException;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CadastroEscopoIT {

    @Autowired
    private EscopoService roleService;

    @Test
    public void cadastrarSucesso() {
        Escopo role = new Escopo();
        role.setNome("GERENTE");
        role = roleService.save(role);
        assertThat(role).isNotNull();
        assertThat(role.getId()).isNotNull();
    }

    @Test(expected = ConstraintViolationException.class)
    public void cadastrarSemNome() {
        Escopo role = new Escopo();
        role.setNome(null);
        role = roleService.save(role);
    }

    @Test(expected = EscopoNaoCadastradoException.class)
    public void naoEncontrada() {
        roleService.deleteById(100L);
    }
}