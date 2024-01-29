package br.com.fiap.tech.challengeii.parquimetrobackend.dtos;

import br.com.fiap.tech.challengeii.parquimetrobackend.enums.TipoPagamentoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record FormasPagamentoDTO(String id,
                                 @NotNull TipoPagamentoEnum tipoPagamento,
                                 @Length(min = 13, max = 16) String numeroCartao,
                                 @Length(min = 3, max = 3) String numeroCVC,
                                 @Length(min = 11, max = 55) String chavesPix,
                                 @NotNull
                                 @NotBlank
                                 String idCondutor) {
}
