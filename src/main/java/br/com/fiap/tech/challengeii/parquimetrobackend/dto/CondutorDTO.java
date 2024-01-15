package br.com.fiap.tech.challengeii.parquimetrobackend.dto;

public record CondutorDTO(
        Long id,
        String nome,
        String endereco,
        String email,
        String numCelular

) {
}
