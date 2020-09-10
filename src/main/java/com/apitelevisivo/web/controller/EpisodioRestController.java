package com.apitelevisivo.web.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.apitelevisivo.model.Episodio;
import com.apitelevisivo.model.dto.converter.ConverterEpisodio;
import com.apitelevisivo.model.dto.out.EpisodioOut;
import com.apitelevisivo.service.EpisodioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Episódio")
@RestController
@RequestMapping(value = "/api/episodio")
public class EpisodioRestController {

    // private static final String EPISODIOS = "episódios";

    @Autowired
    private EpisodioService episodioService;

    @Autowired
    private ConverterEpisodio converterEpisodio;

    @ApiOperation("Buscar episódio por id")
	@ResponseBody
	@GetMapping("/alterar/{id}")
	public EpisodioOut buscarAlterar(@ApiParam(value = "ID de um episódio", example = "1") @PathVariable Long id) {
		Episodio episodio = episodioService.getOne(id);
		EpisodioOut episodioOut = converterEpisodio.toModel(episodio);
		episodioOut.add(linkTo(methodOn(EpisodioRestController.class).buscarAlterar(episodioOut.getId())).withSelfRel());
		// episodioOut.add(linkTo(methodOn(EpisodioRestController.class).listar()).withRel(USUARIOS));
		return episodioOut;
	}

    // @ApiOperation("Adicionar episódio")
    // @ResponseBody
    // @PostMapping(value = "/adicionar")
    // @ResponseStatus(HttpStatus.OK)
    // public EpisodioOut registrar(@ApiParam(name = "Dados do episódio", value="Representação de um usuário" ) EpisodioIn in) {
    //     Episodio episodio = converterEpisodio.converterInToEpisodio(in);
	// 	return converterEpisodio.converterEpisodioToOut(episodio);
    // }

    // @ApiOperation("Alterar episódio por id")
    // @ResponseBody
    // @PutMapping("/alterar/{id}")
    // public ResponseEntity<Episodio> alterar(@ApiParam(value = "ID de um episódio", example = "1") @PathVariable Long id, @RequestBody Episodio episodio) {
    //     try {
    //         Episodio episodio2 = episodioService.findById(id);
    //         if (episodio2 != null) {
    //             BeanUtils.copyProperties(episodio, episodio2);
    //             episodio2 = episodioService.update(episodio2);
    //             return ResponseEntity.ok(episodio2);
    //         }
    //     } catch (EpisodioNaoCadastradoException e) {
    //         throw new NegocioException("O episodio não existe no sistema");
    //     }
    //     return ResponseEntity.notFound().build();
    // }

    // @ApiOperation("Buscar episódio por id")
	// @ResponseBody
	// @GetMapping("/buscar/{id}")
	// public EpisodioOut buscar(@ApiParam(value = "ID de um episódio", example = "1") @PathVariable Long id) {
    //     Episodio episodio = episodioService.getOne(id);
	// 	EpisodioOut episodioOut = converterEpisodio.converterEpisodioToOut(episodio);
	// 	episodioOut.add(linkTo(methodOn(EpisodioRestController.class).buscar(episodioOut.getId())).withSelfRel());
	// 	episodioOut.add(linkTo(methodOn(EpisodioRestController.class).listar()).withRel(EPISODIOS));
	// 	return episodioOut;
    // }
    
    @ApiOperation("Remover episódio por id")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Episodio> remover(@PathVariable Long id) {
        episodioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}