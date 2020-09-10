package com.apitelevisivo.web.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.apitelevisivo.model.Servico;
import com.apitelevisivo.model.dto.converter.ConverterServico;
import com.apitelevisivo.model.dto.out.ServicoOut;
import com.apitelevisivo.service.JasperReportsService;
import com.apitelevisivo.service.ServicoService;

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

@Api(tags = "Serviço")
@RestController
@RequestMapping(value = "/api/servico")
public class ServicoRestController {

    // private static final String SERVICOS = "serviços";
    private static final String NOME = "nome";

    @Autowired
    private ServicoService servicoService;

    @Autowired
    private ConverterServico converterServico;

    @Autowired
    private JasperReportsService jasperReportsService;
    
    @Autowired
    private PagedResourcesAssembler<Servico> pagedResourcesAssembler;

    @ApiOperation("Listar serviços")
    @ResponseBody
    @GetMapping(value = "/listar")
    public PagedModel<ServicoOut> listar(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "dir", defaultValue = "asc") String dir) {

        Pageable pageable = null;

        if (dir.equalsIgnoreCase("asc")) {
            pageable = PageRequest.of(page, limit, Sort.by(Direction.ASC, NOME));
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(Direction.DESC, NOME));
        }

        Page<Servico> listaServico = servicoService.findAll(pageable);
        return pagedResourcesAssembler.toModel(listaServico, converterServico);
    }

    @ApiOperation("Listar serviços por nome")
    @ResponseBody
    @GetMapping(value = "/listar/{nome}")
    public PagedModel<ServicoOut> listarByName(
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

        Page<Servico> listaServico = servicoService.findAllByName(nome, pageable);
        return pagedResourcesAssembler.toModel(listaServico, converterServico);
    }

    @ApiOperation("Buscar serviço por id")
	@ResponseBody
	@GetMapping("/alterar/{id}")
	public ServicoOut buscarAlterar(@ApiParam(value = "ID de um serviço", example = "1") @PathVariable Long id) {
		Servico servico = servicoService.getOne(id);
		ServicoOut servicoOut = converterServico.toModel(servico);
		servicoOut.add(linkTo(methodOn(ServicoRestController.class).buscarAlterar(servicoOut.getId())).withSelfRel());
		// servicoOut.add(linkTo(methodOn(ServicoRestController.class).listar()).withRel(USUARIOS));
		return servicoOut;
	}

    // @ApiOperation("Adicionar serviço")
    // @ResponseBody
    // @PostMapping(value = "/adicionar")
    // @ResponseStatus(HttpStatus.OK)
    // public ServicoOut registrar(@ApiParam(name = "Dados do serviço", value="Representação de um usuário" ) ServicoIn in) {
    //     Servico servico = converterServico.converterInToServico(in);
	// 	return converterServico.converterServicoToOut(servico);
    // }

    // @ApiOperation("Alterar serviço por id")
    // @ResponseBody
    // @PutMapping("/alterar/{id}")
    // public ResponseEntity<Servico> alterar(@ApiParam(value = "ID de um serviço", example = "1") @PathVariable Long id, @RequestBody Servico servico) {
    //     try {
    //         Servico servico2 = servicoService.findById(id);
    //         if (servico2 != null) {
    //             BeanUtils.copyProperties(servico, servico2);
    //             servico2 = servicoService.update(servico2);
    //             return ResponseEntity.ok(servico2);
    //         }
    //     } catch (ServicoNaoCadastradoException e) {
    //         throw new NegocioException("O servico não existe no sistema");
    //     }
    //     return ResponseEntity.notFound().build();
    // }

    // @ApiOperation("Buscar sserviço por id")
	// @ResponseBody
	// @GetMapping("/buscar/{id}")
	// public ServicoOut buscar(@ApiParam(value = "ID de um serviço", example = "1") @PathVariable Long id) {
    //     Servico servico = servicoService.getOne(id);
	// 	ServicoOut servicoOut = converterServico.converterServicoToOut(servico);
	// 	servicoOut.add(linkTo(methodOn(ServicoRestController.class).buscar(servicoOut.getId())).withSelfRel());
	// 	servicoOut.add(linkTo(methodOn(ServicoRestController.class).listar()).withRel(SERVICOS));
	// 	return servicoOut;
    // }
    
    @ApiOperation("Remover serviço por id")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Servico> remover(@PathVariable Long id) {
        servicoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation("Relatório de serviços em pdf")
    @GetMapping("/pdf")
    public ResponseEntity<byte[]> imprimeRelatorioPdf() {
    	byte[] relatorio = jasperReportsService.imprimeRelatorioNoNavegador("servico");
    	return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(relatorio);
    }
}