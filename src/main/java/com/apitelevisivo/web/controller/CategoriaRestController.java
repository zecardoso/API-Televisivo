package com.apitelevisivo.web.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import com.apitelevisivo.model.Categoria;
import com.apitelevisivo.model.dto.converter.ConverterCategoria;
import com.apitelevisivo.model.dto.in.CategoriaIn;
import com.apitelevisivo.model.dto.out.CategoriaOut;
import com.apitelevisivo.service.CategoriaService;
import com.apitelevisivo.service.JasperReportsService;
import com.apitelevisivo.service.exceptions.CategoriaNaoCadastradaException;
import com.apitelevisivo.service.exceptions.NegocioException;

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

@Api(tags = "Categoria")
@RestController
@RequestMapping(value = "/api/categoria")
public class CategoriaRestController {

    private static final String CATEGORIAS = "categorias";

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ConverterCategoria converterCategoria;

    @Autowired
    private JasperReportsService jasperReportsService;
    
    @ApiOperation("Listar categorias")
    @ResponseBody
    @GetMapping(value = "/listar")
    public CollectionModel<CategoriaOut> listar() {
        List<Categoria> listaCategoria = categoriaService.findAll();
        List<CategoriaOut> listaCategoriaOut = converterCategoria.toCollectionsModel(listaCategoria);
        listaCategoriaOut.forEach(categoriaOut -> {
			categoriaOut.add(linkTo(methodOn(CategoriaRestController.class).alterar(categoriaOut.getId(), new Categoria())).withSelfRel());
		});
        CollectionModel<CategoriaOut> categoriaCollectionsModel = new CollectionModel<>(listaCategoriaOut);
        categoriaCollectionsModel.add(linkTo(methodOn(CategoriaRestController.class).listar()).withRel(CATEGORIAS));
        return categoriaCollectionsModel;
    }

    @ApiOperation("Adicionar categoria")
    @ResponseBody
    @PostMapping(value = "/adicionar")
    @ResponseStatus(HttpStatus.OK)
    public CategoriaOut registrar(@ApiParam(name = "Dados do Usuário", value="Representação de um categoria" ) CategoriaIn in) {
        Categoria categoria = converterCategoria.converterInToCategoria(in);
		return converterCategoria.converterCategoriaToOut(categoria);
    }

    @ApiOperation("Alterar categoria por id")
    @ResponseBody
    @PutMapping("/alterar/{id}")
    public ResponseEntity<Categoria> alterar(@ApiParam(value = "ID de um Usuário", example = "1") @PathVariable Long id, @RequestBody Categoria categoria) {
        try {
            Categoria categoria2 = categoriaService.findById(id);
            if (categoria2 != null) {
                BeanUtils.copyProperties(categoria, categoria2);
                categoria2 = categoriaService.update(categoria2);
                return ResponseEntity.ok(categoria2);
            }
        } catch (CategoriaNaoCadastradaException e) {
            throw new NegocioException("O categoria não existe no sistema");
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation("Buscar categoria por id")
	@ResponseBody
	@GetMapping("/buscar/{id}")
	public CategoriaOut buscar(@ApiParam(value = "ID de um Usuário", example = "1") @PathVariable Long id) {
        Categoria categoria = categoriaService.getOne(id);
		CategoriaOut categoriaOut = converterCategoria.converterCategoriaToOut(categoria);
		categoriaOut.add(linkTo(methodOn(CategoriaRestController.class).buscar(categoriaOut.getId())).withSelfRel());
		categoriaOut.add(linkTo(methodOn(CategoriaRestController.class).listar()).withRel(CATEGORIAS));
		return categoriaOut;
    }
    
    @ApiOperation("Remover categoria por id")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Categoria> remover(@PathVariable Long id) {
        categoriaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @ApiOperation("Relatório de categorias em pdf")
    @GetMapping("/pdf")
    public ResponseEntity<byte[]> imprimeRelatorioPdf() {
    	byte[] relatorio = jasperReportsService.imprimeRelatorioNoNavegador("categoria");
    	HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=categoria.pdf");
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).headers(headers).body(relatorio);
    }
}