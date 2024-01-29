package br.com.fiap.tech.challengeii.parquimetrobackend.mappers;

import br.com.fiap.tech.challengeii.parquimetrobackend.dtos.CondutoresDTO;
import br.com.fiap.tech.challengeii.parquimetrobackend.dtos.VeiculosDTO;
import br.com.fiap.tech.challengeii.parquimetrobackend.models.Condutores;
import br.com.fiap.tech.challengeii.parquimetrobackend.models.Veiculos;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VeiculosMapper {

    Veiculos toVeiculos(VeiculosDTO dto);
    VeiculosDTO toVeiculosDTO(Veiculos entity);

}
