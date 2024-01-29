package br.com.fiap.tech.challengeii.parquimetrobackend.mappers;

import br.com.fiap.tech.challengeii.parquimetrobackend.dtos.FormasPagamentoDTO;
import br.com.fiap.tech.challengeii.parquimetrobackend.models.FormasPagamento;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FormasPagamentoMapper {

    FormasPagamento toFormasPagamento(FormasPagamentoDTO dto);
    FormasPagamentoDTO toFormasPagamentoDTO(FormasPagamento entity);

}
