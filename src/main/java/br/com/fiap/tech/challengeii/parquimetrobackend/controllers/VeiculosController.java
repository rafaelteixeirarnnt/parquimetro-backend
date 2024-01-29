package br.com.fiap.tech.challengeii.parquimetrobackend.controllers;

import br.com.fiap.tech.challengeii.parquimetrobackend.dtos.CondutoresDTO;
import br.com.fiap.tech.challengeii.parquimetrobackend.dtos.VeiculosDTO;
import br.com.fiap.tech.challengeii.parquimetrobackend.models.Veiculos;
import br.com.fiap.tech.challengeii.parquimetrobackend.service.VeiculosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static br.com.fiap.tech.challengeii.parquimetrobackend.utils.ApiUtils.API;

@RestController
@RequiredArgsConstructor
@RequestMapping(API + "/veiculos")
@Tag(name = "2 - Veículos", description = "Serviços disponíveis para gerenciamento de veículos.")
public class VeiculosController {

    private final VeiculosService service;

    @PostMapping
    @Operation(summary = "Serviço responsável por cadastrar/atualizar veículos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veículo cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Falha no cadastro do condutor"),
    })
    public ResponseEntity<CondutoresDTO> salvar(@Valid @RequestBody List<VeiculosDTO> dtos) {
        this.service.salvar(dtos);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/obterTodosPaginado")
    @Operation(summary = "Serviço responsável por obter veículos paginados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veículos recuperados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Falha no cadastro do condutor"),
    })
    public Page<Veiculos> obterTodosPaginados(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size,
                                               @RequestParam(defaultValue = "ano") String sortBy,
                                               @RequestParam(required = false) String placa,
                                               @RequestParam(required = false) String ano,
                                               @RequestParam(required = false) String modelo,
                                               @RequestParam(required = false) String cor) {
        return this.service.obterTodosPaginado(placa, ano, modelo, cor, PageRequest.of(page, size, Sort.by(sortBy)));
    }

}
