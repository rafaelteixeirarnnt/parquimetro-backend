package br.com.fiap.tech.challengeii.parquimetrobackend.controllers;

import br.com.fiap.tech.challengeii.parquimetrobackend.controllers.exception.ApplicationException;
import br.com.fiap.tech.challengeii.parquimetrobackend.dtos.CondutoresDTO;
import br.com.fiap.tech.challengeii.parquimetrobackend.dtos.FormasPagamentoDTO;
import br.com.fiap.tech.challengeii.parquimetrobackend.dtos.PaginatorDTO;
import br.com.fiap.tech.challengeii.parquimetrobackend.service.CondutoresService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

import static br.com.fiap.tech.challengeii.parquimetrobackend.utils.ApiUtils.API;

@RestController
@RequiredArgsConstructor
@RequestMapping(API + "/condutores")
@Tag(name = "1 - Condutores", description = "Serviços disponíveis para gerenciamento de condutores.")
public class CondutoresController {

    private final CondutoresService condutoresService;

    @PostMapping
    @Operation(summary = "Serviço responsável por cadastrar condutores.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Condutor cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Falha no cadastro do condutor"),
    })
    public ResponseEntity<CondutoresDTO> salvar(@Valid @RequestBody CondutoresDTO dto) {
        var condutor = condutoresService.salvar(dto);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(condutor.id()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Serviço responsável por cadastrar condutores.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Condutor cadastrado com sucesso", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CondutoresDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Falha no cadastro do condutor"),
    })
    public ResponseEntity<CondutoresDTO> atualizar(@PathVariable String id, @Valid @RequestBody CondutoresDTO dto) {
        try {
            var condutor = condutoresService.atualizar(id, dto);
            return ResponseEntity.ok(condutor);
        } catch (ApplicationException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Serviço responsável por recuperar os condutores.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Condutor encontrado", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CondutoresDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Condutor não localizado"),
    })
    public ResponseEntity<CondutoresDTO> obterCondutorPorId(@PathVariable String id) {
        var condutor = condutoresService.obterCondutorPorId(id);
        return ResponseEntity.ok(condutor);
    }

    @PostMapping("/pesquisar")
    @Operation(summary = "Serviço responsável por recuperar os condutores.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Condutor encontrado", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CondutoresDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Condutor não localizado"),
    })
    public ResponseEntity<Page<CondutoresDTO>> obterTodosCondutores(@NotNull @RequestBody PaginatorDTO paginator) {
        return ResponseEntity.ok(condutoresService.obterTodos(paginator));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Serviço responsável por deletar condutor.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Condutor deletado", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "Condutor não localizado"),
    })
    public ResponseEntity<Void> deletarCondutor(@PathVariable String id) {
        this.condutoresService.deletarCondutor(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/cadastrar-formas-pagamento")
    @Operation(summary = "Serviço responsável por cadastrar as formas de pagamento do condutor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Formas de pagamento cadastradas", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "Condutor não localizado"),
    })
    public ResponseEntity<Void> vincularVeiculo(@Valid @RequestBody List<FormasPagamentoDTO> dtos, @PathVariable String id) {
        this.condutoresService.cadastrarFormasPagamento(id, dtos);
        return ResponseEntity.noContent().build();
    }

}
