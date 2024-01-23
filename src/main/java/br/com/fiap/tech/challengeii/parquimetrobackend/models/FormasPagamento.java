package br.com.fiap.tech.challengeii.parquimetrobackend.models;

import br.com.fiap.tech.challengeii.parquimetrobackend.enums.TipoPagamentoEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
@AllArgsConstructor
public class FormasPagamento {

    @Id
    private String id;

    private TipoPagamentoEnum tipoPagamento;

    @DBRef
    private Condutores condutor;

}
