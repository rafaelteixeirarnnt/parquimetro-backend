package br.com.fiap.tech.challengeii.parquimetrobackend.service;

import br.com.fiap.tech.challengeii.parquimetrobackend.controllers.exception.ApplicationException;
import br.com.fiap.tech.challengeii.parquimetrobackend.dtos.CondutoresDTO;
import br.com.fiap.tech.challengeii.parquimetrobackend.dtos.PaginatorDTO;
import br.com.fiap.tech.challengeii.parquimetrobackend.dtos.VeiculosDTO;
import br.com.fiap.tech.challengeii.parquimetrobackend.mappers.CondutoresMapper;
import br.com.fiap.tech.challengeii.parquimetrobackend.mappers.VeiculosMapper;
import br.com.fiap.tech.challengeii.parquimetrobackend.models.Condutores;
import br.com.fiap.tech.challengeii.parquimetrobackend.models.Veiculos;
import br.com.fiap.tech.challengeii.parquimetrobackend.repositories.CondutorRepository;
import br.com.fiap.tech.challengeii.parquimetrobackend.repositories.VeiculosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CondutoresService {

    private final CondutoresMapper mapper;
    private final MongoTemplate mongoTemplate;
    private final VeiculosMapper veiculosMapper;
    private final VeiculosRepository veiculosRepository;
    private final CondutorRepository repository;

    public CondutoresDTO salvar(CondutoresDTO dto) {
        Optional<Condutores> condutoresOptional = this.repository.findByCpf(dto.cpf());
        if (condutoresOptional.isPresent()) {
            throw new ApplicationException("Usuário já cadastrado");
        }
        Condutores condutor = this.mapper.toCondutores(dto);
        condutor.setId(null);

        if (Objects.nonNull(condutor.getVeiculos())) {
            this.veiculosRepository.saveAll(condutor.getVeiculos());
        }

        return this.mapper.toCondutoresDTO(this.repository.save(condutor));
    }

    public CondutoresDTO atualizar(String id, CondutoresDTO dto) {
        this.repository.findByIdAndCpf(id, dto.cpf()).orElseThrow(() -> new ApplicationException("Condutor não localizado"));

        Query query = new Query(Criteria.where("_id").is(id));

        Update update = new Update();
        update.set("nome", dto.nome());
        update.set("email", dto.email());
        update.set("celular", dto.celular());
        update.set("endereco", dto.endereco());
        update.set("dtNascimento", dto.dtNascimento());

        this.mongoTemplate.updateFirst(query, update, Condutores.class);

        var condutor = this.repository.findById(id).orElseThrow(() -> new ApplicationException("Condutor não localizado"));

        return this.mapper.toCondutoresDTO(condutor);
    }

    public CondutoresDTO obterCondutorPorId(String id) {
        if (Objects.isNull(id) || id.isBlank()) {
            throw new ApplicationException("Parâmetro informado inválido");
        }
        var condutor = this.repository.findById(id).orElseThrow(() -> new ApplicationException("Condutor não localizado"));
        return this.mapper.toCondutoresDTO(condutor);
    }

    public Page<CondutoresDTO> obterTodos(PaginatorDTO paginator) {
        PageRequest pageRequest = PageRequest.of(paginator.getPage(), paginator.getLinesPerPage());
        Query query = new Query();

        if (Objects.nonNull(paginator) && Objects.nonNull(paginator.getParams())) {
            if (Objects.nonNull(paginator.getParams().get("nome"))) {
                query.addCriteria(Criteria.where("nome").is(paginator.getParams().get("nome")));
            }
            if (Objects.nonNull(paginator.getParams().get("cpf"))) {
                query.addCriteria(Criteria.where("cpf").is(paginator.getParams().get("cpf")));
            }
        }

        query.with(pageRequest);
        long total = this.mongoTemplate.count(query, Condutores.class);
        List<CondutoresDTO> condutores = mongoTemplate.find(query, Condutores.class).stream().map(this.mapper::toCondutoresDTO).toList();
        return PageableExecutionUtils.getPage(condutores, pageRequest, () -> total);
    }

    public void deletarCondutor(String id) {
        if (Objects.isNull(id) || id.isBlank()) {
            throw new ApplicationException("Parâmetro informado inválido");
        }
        var condutor = this.repository.findById(id).orElseThrow(() -> new ApplicationException("Condutor não localizado"));
        this.repository.delete(condutor);
    }

    public void vincularVeiculo(String id, List<VeiculosDTO> dtos) {
        if (Objects.isNull(id) || id.isBlank()) {
            throw new ApplicationException("Parâmetro informado inválido");
        }

        List<Veiculos> veiculos = dtos.stream().map(this.veiculosMapper::toVeiculos)
                .peek(vei -> vei.setId(null))
                .toList();

        var condutor = this.repository.findById(id).orElseThrow(() -> new ApplicationException("Condutor não localizado"));

        if (Objects.nonNull(condutor.getVeiculos())) {
            List<Veiculos> veiculosASalvar = dtos
                    .stream().filter(v -> !condutor.getVeiculos().stream().map(Veiculos::getPlaca).toList().contains(v.placa()))
                    .toList().stream().map(this.veiculosMapper::toVeiculos).toList();
            List<Veiculos> veiculosAAtualizar = dtos
                    .stream().filter(v -> condutor.getVeiculos().stream().map(Veiculos::getPlaca).toList().contains(v.placa()))
                    .toList().stream().map(this.veiculosMapper::toVeiculos)
                    .toList();
            List<Veiculos> veiculosRemovidos = condutor.getVeiculos().stream().filter(v -> !dtos.stream().map(VeiculosDTO::placa).toList().contains(v.getPlaca())).toList();

            List<Veiculos> veiculosSalvo = this.veiculosRepository.saveAll(veiculosASalvar.stream()
                    .peek(vei -> vei.setId(null))
                    .toList());

            List<Veiculos> veiculosAtualizados = this.veiculosRepository.saveAll(veiculosAAtualizar);
            this.veiculosRepository.deleteAll(veiculosRemovidos);

            veiculosSalvo.addAll(veiculosAtualizados);

            Update update = new Update();
            update.set("veiculos", veiculosSalvo);

            Query query = new Query(Criteria.where("_id").is(id));

            this.mongoTemplate.updateFirst(query, update, Condutores.class);

        } else {
            this.veiculosRepository.saveAll(veiculos);
            Update update = new Update();
            update.set("veiculos", veiculos);

            Query query = new Query(Criteria.where("_id").is(id));

            this.mongoTemplate.updateFirst(query, update, Condutores.class);
        }
    }

}
