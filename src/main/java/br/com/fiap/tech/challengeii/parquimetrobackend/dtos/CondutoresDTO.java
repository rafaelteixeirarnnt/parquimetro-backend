package br.com.fiap.tech.challengeii.parquimetrobackend.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;
import java.util.List;

public record CondutoresDTO(
        String id,
        @NotNull @NotBlank
        @Length(min = 2, message = "O nome do condutor precisar ter no mínimo 2 caracteres")
        @Length(max = 50, message = "O nome do condutor não pode ter mais de 50 caracteres")
        String nome,
        @CPF String cpf,
        @NotNull LocalDateTime dtNascimento,
        @NotNull @NotBlank @Length(min = 10, message = "O endereço do condutor deve possuir no mínimo 10 caracteres")
        @Length(max = 150, message = "O endereço do condutor não pode ter mais de 150 caracteres")
        String endereco,
        @Email(message = "Informe um e-mail válido") String email,
        @NotNull @NotBlank @Length(min = 11, max = 11, message = "O telefone do condutor deve ter 11 caracteres sem caracteres especiais")
        String celular,
        @Valid List<VeiculosDTO> veiculos) {
}
