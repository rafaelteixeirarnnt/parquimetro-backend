package br.com.fiap.tech.challengeii.parquimetrobackend.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class PaginatorDTO {

    private Integer page = 0;
    private Integer linesPerPage = 10;
    private Map<String, Object> params;

}
