package com.apitelevisivo.web.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import com.apitelevisivo.model.Usuario;
import com.apitelevisivo.model.dto.converter.ConverterUsuario;
import com.apitelevisivo.model.dto.in.UsuarioIn;
import com.apitelevisivo.model.dto.out.UsuarioOut;
import com.apitelevisivo.service.JasperReportsService;
import com.apitelevisivo.service.UsuarioService;
import com.apitelevisivo.service.exceptions.NegocioException;
import com.apitelevisivo.service.exceptions.UsuarioNaoCadastradoException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

@Api(tags = "Usuário")
@RestController
@RequestMapping(value = "/api/usuario")
public class UsuarioRestController {

    private static final String USUARIOS = "usuarios";

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ConverterUsuario converterUsuario;

    @Autowired
    private JasperReportsService jasperReportsService;
    
    @ApiOperation("listar usuários")
    @ResponseBody
    @GetMapping(value = "/listar")
    public CollectionModel<UsuarioOut> listar() {
        List<Usuario> listaUsuario = usuarioService.findAll();
        List<UsuarioOut> listaUsuarioOut = converterUsuario.toCollectionsModel(listaUsuario);
        listaUsuarioOut.forEach(usuarioOut -> {
			usuarioOut.add(linkTo(methodOn(UsuarioRestController.class).alterar(usuarioOut.getId(), new Usuario())).withSelfRel());
		});
        CollectionModel<UsuarioOut> usuarioCollectionsModel = new CollectionModel<>(listaUsuarioOut);
        usuarioCollectionsModel.add(linkTo(methodOn(UsuarioRestController.class).listar()).withRel(USUARIOS));
        return usuarioCollectionsModel;
    }

    @ApiOperation("Adicionar usuário")
    @ResponseBody
    @PostMapping(value = "/adicionar")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioOut registrar(@ApiParam(name = "Dados do Usuário", value="Representação de um usuário" ) UsuarioIn in) {
        Usuario usuario = converterUsuario.converterInToUsuario(in);
		return converterUsuario.converterUsuarioToOut(usuario);
    }

    @ApiOperation("Alterar usuário por id")
    @ResponseBody
    @PutMapping("/alterar/{id}")
    public ResponseEntity<Usuario> alterar(@ApiParam(value = "ID de um Usuário", example = "1") @PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            Usuario usuario2 = usuarioService.findById(id);
            if (usuario2 != null) {
                BeanUtils.copyProperties(usuario, usuario2);
                usuario2 = usuarioService.update(usuario2);
                return ResponseEntity.ok(usuario2);
            }
        } catch (UsuarioNaoCadastradoException e) {
            throw new NegocioException("O usuario não existe no sistema");
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation("Buscar usuário por id")
	@ResponseBody
	@GetMapping("/buscar/{id}")
	public UsuarioOut buscar(@ApiParam(value = "ID de um Usuário", example = "1") @PathVariable Long id) {
        Usuario usuario = usuarioService.getOne(id);
		UsuarioOut usuarioOut = converterUsuario.converterUsuarioToOut(usuario);
		usuarioOut.add(linkTo(methodOn(UsuarioRestController.class).buscar(usuarioOut.getId())).withSelfRel());
		usuarioOut.add(linkTo(methodOn(UsuarioRestController.class).listar()).withRel(USUARIOS));
		return usuarioOut;
    }

    @ApiOperation("Remover usuário por id")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Usuario> remover(@PathVariable Long id) {
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @ApiOperation("Relatório de usuários em pdf")
    @GetMapping("/pdf")
    public ResponseEntity<byte[]> imprimeRelatorioPdf() {
    	byte[] relatorio = jasperReportsService.imprimeRelatorioNoNavegador("usuario");
    	HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=usuario.pdf");
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).headers(headers).body(relatorio);
    }
}