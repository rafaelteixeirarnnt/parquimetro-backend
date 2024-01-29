package br.com.fiap.tech.challengeii.parquimetrobackend.models;

import br.com.fiap.tech.challengeii.parquimetrobackend.enums.TipoPagamentoEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "db_formas_pagamento")
public class FormasPagamento {

    @Id
    private String id;

    private TipoPagamentoEnum tipoPagamento;
    private String numeroCartao;
    private String numeroCVC;
    private String chavesPix;

    @DBRef
    private Condutores condutor;

}
