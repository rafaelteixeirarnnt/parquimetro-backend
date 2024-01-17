package br.com.fiap.tech.challengeii.parquimetrobackend.dto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class VeiculoDTO {
    Long id;
    String placa;
    String ano;
    String modelo;
    String cor;
}
