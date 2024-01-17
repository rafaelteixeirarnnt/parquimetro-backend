package br.com.fiap.tech.challengeii.parquimetrobackend.mappers;

import br.com.fiap.tech.challengeii.parquimetrobackend.dtos.CondutoresDTO;
import br.com.fiap.tech.challengeii.parquimetrobackend.models.Condutores;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CondutoresMapper {

    Condutores toCondutores(CondutoresDTO dto);
    CondutoresDTO toCondutoresDTO(Condutores entity);

}
