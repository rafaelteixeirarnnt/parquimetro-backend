package br.com.fiap.tech.challengeii.parquimetrobackend.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public record VeiculosDTO(String id,
                          @NotNull @Size(min = 7, max = 7, message = "Informe a \"PLACA\" com 7 caracteres.") String placa,
                          @NotNull @Length(min = 4, max = 4, message = "Informe o \"ANO\" com 4 caracteres.") String ano,
                          @NotNull @Length(min = 2, max = 20, message = "O \"MODELO\" precisa ter no mínimo 2 caracteres e no máximo 20.") String modelo,
                          @NotNull @Length(min = 3, max = 20, message = "A \"COR\" precisa ter no mínimo 3 caracteres e no máximo 20.") String cor) {
}
