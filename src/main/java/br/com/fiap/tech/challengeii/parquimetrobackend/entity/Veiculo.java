package br.com.fiap.tech.challengeii.parquimetrobackend.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "db_veiculos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Veiculo {
    @Id
    private String id;
    private String placa;
    private String ano;
    private String modelo;
    private String cor;
}
