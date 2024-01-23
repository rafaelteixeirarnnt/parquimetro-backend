package br.com.fiap.tech.challengeii.parquimetrobackend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoPagamentoEnum {

    PIX("PIX"),
    CREDITO("Crédito"),
    DEBITO("Débito");

    private final String descricao;

}
