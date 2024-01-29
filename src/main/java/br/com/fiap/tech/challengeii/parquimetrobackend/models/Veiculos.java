package br.com.fiap.tech.challengeii.parquimetrobackend.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Document(collection = "db_veiculos")
public class Veiculos {

    @Id
    private String id;
    private String placa;
    private String ano;
    private String modelo;
    private String cor;

    @DBRef
    private Condutores condutor;

}
