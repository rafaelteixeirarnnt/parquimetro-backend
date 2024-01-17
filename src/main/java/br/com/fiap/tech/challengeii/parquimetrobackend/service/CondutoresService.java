package br.com.fiap.tech.challengeii.parquimetrobackend.service;

import br.com.fiap.tech.challengeii.parquimetrobackend.dtos.CondutoresDTO;
import br.com.fiap.tech.challengeii.parquimetrobackend.mappers.CondutoresMapper;
import br.com.fiap.tech.challengeii.parquimetrobackend.models.Condutores;
import br.com.fiap.tech.challengeii.parquimetrobackend.repositories.CondutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CondutoresService {

    private final CondutoresMapper mapper;
    private final CondutorRepository repository;

    public CondutoresDTO save(CondutoresDTO dto) {
        Condutores condutor = this.mapper.toCondutores(dto);
        condutor.setId(null);
        return this.mapper.toCondutoresDTO(this.repository.save(condutor));
    }

}
