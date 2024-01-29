package br.com.fiap.tech.challengeii.parquimetrobackend.controllers;

import br.com.fiap.tech.challengeii.parquimetrobackend.dtos.FormasPagamentoDTO;
import br.com.fiap.tech.challengeii.parquimetrobackend.service.FormasPagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static br.com.fiap.tech.challengeii.parquimetrobackend.utils.ApiUtils.API;

@RestController
@RequiredArgsConstructor
@RequestMapping(API + "/formas-pagamento")
@Tag(name = "3 - Formas de pagamento", description = "Serviços disponíveis para gerenciamento de formas de pagamento do condutor.")
public class FormasPagamentoController {

    private final FormasPagamentoService service;

    @PostMapping
    @Operation(summary = "Serviço responsável por cadastrar e atualizar as formas de pagamento do condutor.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Formas de pagamento cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Falha no cadastro do condutor"),
    })
    public ResponseEntity<List<FormasPagamentoDTO>> cadastrarAtualizarFormasPagamento(@RequestBody List<FormasPagamentoDTO> dtos) {
        var formasPagamentos = this.service.salvar(dtos);
        return new ResponseEntity<>(formasPagamentos, HttpStatus.CREATED);
    }

}
