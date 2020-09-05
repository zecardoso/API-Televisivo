package com.apitelevisivo.web.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import com.apitelevisivo.model.Servico;
import com.apitelevisivo.model.dto.converter.ConverterServico;
import com.apitelevisivo.model.dto.in.ServicoIn;
import com.apitelevisivo.model.dto.out.ServicoOut;
import com.apitelevisivo.service.JasperReportsService;
import com.apitelevisivo.service.ServicoService;
import com.apitelevisivo.service.exceptions.NegocioException;
import com.apitelevisivo.service.exceptions.ServicoNaoCadastradoException;

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

@Api(tags = "Serviço")
@RestController
@RequestMapping(value = "/api/servico")
public class ServicoRestController {

    private static final String SERVICOS = "erviços";

    @Autowired
    private ServicoService servicoService;

    @Autowired
    private ConverterServico converterServico;

    @Autowired
    private JasperReportsService jasperReportsService;
    
    @ApiOperation("Listar erviços")
    @ResponseBody
    @GetMapping(value = "/listar")
    public CollectionModel<ServicoOut> listar() {
        List<Servico> listaServico = servicoService.findAll();
        List<ServicoOut> listaServicoOut = converterServico.toCollectionsModel(listaServico);
        listaServicoOut.forEach(servicoOut -> {
			servicoOut.add(linkTo(methodOn(ServicoRestController.class).alterar(servicoOut.getId(), new Servico())).withSelfRel());
		});
        CollectionModel<ServicoOut> servicoCollectionsModel = new CollectionModel<>(listaServicoOut);
        servicoCollectionsModel.add(linkTo(methodOn(ServicoRestController.class).listar()).withRel(SERVICOS));
        return servicoCollectionsModel;
    }

    @ApiOperation("Adicionar erviço")
    @ResponseBody
    @PostMapping(value = "/adicionar")
    @ResponseStatus(HttpStatus.OK)
    public ServicoOut registrar(@ApiParam(name = "Dados do Usuário", value="Representação de um usuário" ) ServicoIn in) {
        Servico servico = converterServico.converterInToServico(in);
		return converterServico.converterServicoToOut(servico);
    }

    @ApiOperation("Alterar erviço por id")
    @ResponseBody
    @PutMapping("/alterar/{id}")
    public ResponseEntity<Servico> alterar(@ApiParam(value = "ID de um Usuário", example = "1") @PathVariable Long id, @RequestBody Servico servico) {
        try {
            Servico servico2 = servicoService.findById(id);
            if (servico2 != null) {
                BeanUtils.copyProperties(servico, servico2);
                servico2 = servicoService.update(servico2);
                return ResponseEntity.ok(servico2);
            }
        } catch (ServicoNaoCadastradoException e) {
            throw new NegocioException("O servico não existe no sistema");
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation("Buscar serviço por id")
	@ResponseBody
	@GetMapping("/buscar/{id}")
	public ServicoOut buscar(@ApiParam(value = "ID de um Usuário", example = "1") @PathVariable Long id) {
        Servico servico = servicoService.getOne(id);
		ServicoOut servicoOut = converterServico.converterServicoToOut(servico);
		servicoOut.add(linkTo(methodOn(ServicoRestController.class).buscar(servicoOut.getId())).withSelfRel());
		servicoOut.add(linkTo(methodOn(ServicoRestController.class).listar()).withRel(SERVICOS));
		return servicoOut;
    }
    
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