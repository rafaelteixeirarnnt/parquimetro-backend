package br.com.fiap.tech.challengeii.parquimetrobackend.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "db_condutores")
public class Condutores {

    @Id
    private String id;
    private String nome;
    private String cpf;
    private LocalDateTime dtNascimento;
    private String endereco;
    private String email;
    private String celular;

}
