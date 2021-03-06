package com.apitelevisivo.web.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.apitelevisivo.model.Artista;
import com.apitelevisivo.model.dto.converter.ConverterArtista;
import com.apitelevisivo.model.dto.out.ArtistaOut;
import com.apitelevisivo.service.ArtistaService;
import com.apitelevisivo.service.JasperReportsService;

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

@Api(tags = "Artista")
@RestController
@RequestMapping(value = "/api/artista")
public class ArtistaRestController {

    // private static final String ARTISTAS = "artistas";
    private static final String NOME = "nome";

    @Autowired
    private ArtistaService artistaService;

    @Autowired
    private ConverterArtista converterArtista;

    @Autowired
    private JasperReportsService jasperReportsService;
    
    @Autowired
    private PagedResourcesAssembler<Artista> pagedResourcesAssembler;

    @ApiOperation("Listar artistas")
    @ResponseBody
    @GetMapping(value = "/listar")
    public PagedModel<ArtistaOut> listar(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "dir", defaultValue = "asc") String dir) {

        Pageable pageable = null;

        if (dir.equalsIgnoreCase("asc")) {
            pageable = PageRequest.of(page, limit, Sort.by(Direction.ASC, NOME));
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(Direction.DESC, NOME));
        }

        Page<Artista> listaArtista = artistaService.findAll(pageable);
        return pagedResourcesAssembler.toModel(listaArtista, converterArtista);
    }

    @ApiOperation("Listar artistas por nome")
    @ResponseBody
    @GetMapping(value = "/listar/{nome}")
    public PagedModel<ArtistaOut> listarByName(
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

        Page<Artista> listaArtista = artistaService.findAllByName(nome, pageable);
        return pagedResourcesAssembler.toModel(listaArtista, converterArtista);
    }

    @ApiOperation("Buscar artista por id")
	@ResponseBody
	@GetMapping("/alterar/{id}")
	public ArtistaOut buscarAlterar(@ApiParam(value = "ID de um artista", example = "1") @PathVariable Long id) {
		Artista artista = artistaService.getOne(id);
		ArtistaOut artistaOut = converterArtista.toModel(artista);
		artistaOut.add(linkTo(methodOn(ArtistaRestController.class).buscarAlterar(artistaOut.getId())).withSelfRel());
		// artistaOut.add(linkTo(methodOn(ArtistaRestController.class).listar()).withRel(USUARIOS));
		return artistaOut;
	}

    // @ApiOperation("Adicionar artista")
    // @ResponseBody
    // @PostMapping(value = "/adicionar")
    // @ResponseStatus(HttpStatus.OK)
    // public ArtistaOut registrar(@ApiParam(name = "Dados do artista", value="Representação de um usuário" ) ArtistaIn in) {
    //     Artista artista = converterArtista.converterInToArtista(in);
	// 	return converterArtista.converterArtistaToOut(artista);
    // }

    // @ApiOperation("Alterar artista por id")
    // @ResponseBody
    // @PutMapping("/alterar/{id}")
    // public ResponseEntity<Artista> alterar(@ApiParam(value = "ID de um artista", example = "1") @PathVariable Long id, @RequestBody Artista artista) {
    //     try {
    //         Artista artista2 = artistaService.findById(id);
    //         if (artista2 != null) {
    //             BeanUtils.copyProperties(artista, artista2);
    //             artista2 = artistaService.update(artista2);
    //             return ResponseEntity.ok(artista2);
    //         }
    //     } catch (ArtistaNaoCadastradoException e) {
    //         throw new NegocioException("O artista não existe no sistema");
    //     }
    //     return ResponseEntity.notFound().build();
    // }

    // @ApiOperation("Buscar artista por id")
	// @ResponseBody
	// @GetMapping("/buscar/{id}")
	// public ArtistaOut buscar(@ApiParam(value = "ID de um artista", example = "1") @PathVariable Long id) {
    //     Artista artista = artistaService.getOne(id);
	// 	ArtistaOut artistaOut = converterArtista.converterArtistaToOut(artista);
	// 	artistaOut.add(linkTo(methodOn(ArtistaRestController.class).buscar(artistaOut.getId())).withSelfRel());
	// 	artistaOut.add(linkTo(methodOn(ArtistaRestController.class).listar()).withRel(ARTISTAS));
	// 	return artistaOut;
    // }
    
    @ApiOperation("Remover artista por id")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Artista> remover(@PathVariable Long id) {
        artistaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @ApiOperation("Relatório de artistas em pdf")
    @GetMapping("/pdf")
    public ResponseEntity<byte[]> imprimeRelatorioPdf() {
        byte[] relatorio = jasperReportsService.imprimeRelatorioNoNavegador("artista");
        HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=artista.pdf");
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).headers(headers).body(relatorio);
    }
}