package br.com.fiap.tech.challengeii.parquimetrobackend.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public record VeiculosDTO(String id,
                          @NotNull @Size(min = 7, max = 7) String placa,
                          @NotNull @Length(min = 4, max = 4) String ano,
                          @NotNull @Length(min = 2, max = 20) String modelo,
                          @NotNull @Length(min = 3) String cor) {
}
