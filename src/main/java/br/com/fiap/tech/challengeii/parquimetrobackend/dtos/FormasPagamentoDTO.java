package br.com.fiap.tech.challengeii.parquimetrobackend.dtos;

import br.com.fiap.tech.challengeii.parquimetrobackend.enums.TipoPagamentoEnum;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record FormasPagamentoDTO(String id,
                                 @NotNull TipoPagamentoEnum tipoPagamento,
                                 @Length(max = 5) String agencia,
                                 @Length(min = 1, max = 20) String conta,
                                 @Length(min = 11, max = 55) String chavesPix,
                                 CondutoresDTO condutor) {
}
