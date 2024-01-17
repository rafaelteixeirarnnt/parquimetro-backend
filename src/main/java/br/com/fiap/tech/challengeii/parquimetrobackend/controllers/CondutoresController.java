package br.com.fiap.tech.challengeii.parquimetrobackend.controllers;

import br.com.fiap.tech.challengeii.parquimetrobackend.dtos.CondutoresDTO;
import br.com.fiap.tech.challengeii.parquimetrobackend.service.CondutoresService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
            @ApiResponse(responseCode = "201",
                    description = "Condutor cadastrado com sucesso", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            })})
    public ResponseEntity<CondutoresDTO> save(@Valid @RequestBody CondutoresDTO dto) {
        var condutor = condutoresService.save(dto);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(condutor.id()).toUri();
        return ResponseEntity.created(location).build();
    }

}
