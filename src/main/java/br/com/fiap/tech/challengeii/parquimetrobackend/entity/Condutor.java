package br.com.fiap.tech.challengeii.parquimetrobackend.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Objects;

@Getter
@Setter
public class Condutor {

    @Id
    private Long id;

    private String nome;
    private String endereco;
    private String email;
    private String numCelular;

    public Condutor(){}

    public Condutor(Long id, String nome, String endereco, String email, String numCelular) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.email = email;
        this.numCelular = numCelular;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Condutor condutor)) return false;
        return Objects.equals(getId(), condutor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Condutor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", endereco='" + endereco + '\'' +
                ", email='" + email + '\'' +
                ", numCelular='" + numCelular + '\'' +
                '}';
    }
}
