package com.apitelevisivo.web.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.apitelevisivo.model.Usuario;
import com.apitelevisivo.model.dto.converter.ConverterUsuario;
import com.apitelevisivo.model.dto.out.UsuarioOut;
import com.apitelevisivo.service.JasperReportsService;
import com.apitelevisivo.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Usuário")
@RestController
@RequestMapping(value = "/api/usuario")
public class UsuarioRestController {

    // private static final String USUARIOS = "usuarios";
    private static final String USERNAME = "username";

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ConverterUsuario converterUsuario;

    @Autowired
    private JasperReportsService jasperReportsService;
    
    @Autowired
    private PagedResourcesAssembler<Usuario> pagedResourcesAssembler;

    @ApiOperation("listar usuários")
    @ResponseBody
    @GetMapping(value = "/listar")
    public PagedModel<UsuarioOut> listar(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "dir", defaultValue = "asc") String dir) {

        Pageable pageable = null;

        if (dir.equalsIgnoreCase("asc")) {
            pageable = PageRequest.of(page, limit, Sort.by(Direction.ASC, USERNAME));
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(Direction.DESC, USERNAME));
        }

        Page<Usuario> listaUsuario = usuarioService.findAll(pageable);
        return pagedResourcesAssembler.toModel(listaUsuario, converterUsuario);
    }

    @ApiOperation("listar usuários por username")
    @ResponseBody
    @GetMapping(value = "/listar/{username}")
    public PagedModel<UsuarioOut> listarByName(
            @PathVariable(USERNAME) String username,    
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "dir", defaultValue = "asc") String dir) {

        Pageable pageable = null;

        if (dir.equalsIgnoreCase("asc")) {
            pageable = PageRequest.of(page, limit, Sort.by(Direction.ASC, USERNAME));
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(Direction.DESC, USERNAME));
        }

        Page<Usuario> listaUsuario = usuarioService.findAllByName(username, pageable);
        return pagedResourcesAssembler.toModel(listaUsuario, converterUsuario);
    }

    // @ApiOperation("Adicionar usuário")
    // @ResponseBody
    // @PostMapping(value = "/adicionar")
    // @ResponseStatus(HttpStatus.OK)
    // public UsuarioOut registrar(@ApiParam(name = "Dados do Usuário", value = "Representação de um usuário" ) UsuarioIn in) {
    //     Usuario usuario = converterUsuario.converterInToUsuario(in);
	// 	return converterUsuario.toModel(usuario);
    // }

    @ApiOperation("Buscar usuário por id")
	@ResponseBody
	@GetMapping("/alterar/{id}")
	public UsuarioOut buscarAlterar(@ApiParam(value = "ID de um Usuário", example = "1") @PathVariable Long id) {
		Usuario usuario = usuarioService.getOne(id);
		UsuarioOut usuarioOut = converterUsuario.toModel(usuario);
		usuarioOut.add(linkTo(methodOn(UsuarioRestController.class).buscarAlterar(usuarioOut.getId())).withSelfRel());
		// usuarioOut.add(linkTo(methodOn(UsuarioRestController.class).listar()).withRel(USUARIOS));
		return usuarioOut;
	}

    // @ApiOperation("Alterar usuário por id")
    // @ResponseBody
    // @PutMapping("/alterar/{id}")
    // public ResponseEntity<Usuario> alterar(@ApiParam(value = "ID de um Usuário", example = "1") @PathVariable Long id, @RequestBody Usuario usuario) {
    //     try {
    //         Usuario usuario2 = usuarioService.findById(id);
    //         if (usuario2 != null) {
    //             BeanUtils.copyProperties(usuario, usuario2);
    //             usuario2 = usuarioService.update(usuario2);
    //             return ResponseEntity.ok(usuario2);
    //         }
    //     } catch (UsuarioNaoCadastradoException e) {
    //         throw new NegocioException("O usuario não existe no sistema");
    //     }
    //     return ResponseEntity.notFound().build();
    // }

    // @ApiOperation("Buscar usuário por id")
	// @ResponseBody
	// @GetMapping("/buscar/{id}")
	// public UsuarioOut buscar(@ApiParam(value = "ID de um Usuário", example = "1") @PathVariable Long id) {
    //     Usuario usuario = usuarioService.getOne(id);
	// 	UsuarioOut usuarioOut = converterUsuario.converterUsuarioToOut(usuario);
	// 	usuarioOut.add(linkTo(methodOn(UsuarioRestController.class).buscar(usuarioOut.getId())).withSelfRel());
	// 	// usuarioOut.add(linkTo(methodOn(UsuarioRestController.class).listar()).withRel(USUARIOS));
	// 	return usuarioOut;
    // }

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

        // List<UsuarioOut> listaUsuarioOut = converterUsuario.toCollectionsModel(listaUsuario);
        // listaUsuarioOut.forEach(usuarioOut -> {
		// 	usuarioOut.add(linkTo(methodOn(UsuarioRestController.class).alterar(usuarioOut.getId(), new Usuario())).withSelfRel());
		// });
        // CollectionModel<UsuarioOut> usuarioCollectionsModel = new CollectionModel<>(listaUsuarioOut);
        // usuarioCollectionsModel.add(linkTo(methodOn(UsuarioRestController.class).listar()).withRel(USUARIOS));