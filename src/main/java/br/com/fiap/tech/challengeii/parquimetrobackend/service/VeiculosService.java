package br.com.fiap.tech.challengeii.parquimetrobackend.service;

import br.com.fiap.tech.challengeii.parquimetrobackend.controllers.exception.ApplicationException;
import br.com.fiap.tech.challengeii.parquimetrobackend.dtos.VeiculosDTO;
import br.com.fiap.tech.challengeii.parquimetrobackend.mappers.VeiculosMapper;
import br.com.fiap.tech.challengeii.parquimetrobackend.models.Veiculos;
import br.com.fiap.tech.challengeii.parquimetrobackend.repositories.CondutorRepository;
import br.com.fiap.tech.challengeii.parquimetrobackend.repositories.VeiculosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VeiculosService {

    private final VeiculosMapper mapper;
    private final MongoTemplate mongoTemplate;
    private final VeiculosRepository repository;
    private final CondutorRepository condutorRepository;

    public List<VeiculosDTO> salvar(List<VeiculosDTO> dtos) {
        return dtos.stream()
                .map(this::salvarVeiculo)
                .map(this.mapper::toVeiculosDTO)
                .toList();
    }

    private Veiculos salvarVeiculo(VeiculosDTO dto) {
        // Verificar se a placa e modelo já estão cadastrados para o condutor
        Veiculos existente = this.repository.findByPlacaAndModeloAndCondutor_Id(dto.placa(), dto.modelo(), dto.idCondutor()).orElse(null);

        if (existente != null) {
            // Atualizar os campos necessários
            existente.setAno(dto.ano());
            existente.setCor(dto.cor());
            existente.setPlaca(dto.placa());
            existente.setModelo(dto.modelo());

            var condutor = this.condutorRepository.findById(dto.idCondutor()).orElseThrow(() -> new ApplicationException("Condutor não localizado"));
            existente.setCondutor(condutor);
            return this.repository.save(existente);
        } else {
            // Se não existir, salvar um novo veículo
            Veiculos veiculo = this.mapper.toVeiculos(dto);
            var condutor = this.condutorRepository.findById(dto.idCondutor()).orElseThrow(() -> new ApplicationException("Condutor não localizado"));
            veiculo.setId(null);
            veiculo.setCondutor(condutor);
            return this.repository.save(veiculo);
        }
    }

    public Page<Veiculos> obterTodosPaginado(String placa, String ano, String modelo, String cor, PageRequest request) {
        Query query = new Query();

        if (placa != null && !placa.isEmpty()) {
            query.addCriteria(Criteria.where("placa").regex(placa, "i"));
        }

        if (ano != null && !ano.isEmpty()) {
            query.addCriteria(Criteria.where("ano").is(ano));
        }

        if (modelo != null && !modelo.isEmpty()) {
            query.addCriteria(Criteria.where("modelo").regex(modelo, "i"));
        }

        if (cor != null && !cor.isEmpty()) {
            query.addCriteria(Criteria.where("cor").regex(cor, "i"));
        }

        query.with(request);

        long count = this.mongoTemplate.count(query, Veiculos.class);

        return PageableExecutionUtils.getPage(
                mongoTemplate.find(query, Veiculos.class),
                request, () -> count);
    }

}
