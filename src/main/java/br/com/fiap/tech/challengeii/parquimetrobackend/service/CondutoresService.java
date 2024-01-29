package br.com.fiap.tech.challengeii.parquimetrobackend.service;

import br.com.fiap.tech.challengeii.parquimetrobackend.controllers.exception.ApplicationException;
import br.com.fiap.tech.challengeii.parquimetrobackend.dtos.CondutoresDTO;
import br.com.fiap.tech.challengeii.parquimetrobackend.dtos.FormasPagamentoDTO;
import br.com.fiap.tech.challengeii.parquimetrobackend.dtos.PaginatorDTO;
import br.com.fiap.tech.challengeii.parquimetrobackend.enums.TipoPagamentoEnum;
import br.com.fiap.tech.challengeii.parquimetrobackend.mappers.CondutoresMapper;
import br.com.fiap.tech.challengeii.parquimetrobackend.mappers.FormasPagamentoMapper;
import br.com.fiap.tech.challengeii.parquimetrobackend.models.Condutores;
import br.com.fiap.tech.challengeii.parquimetrobackend.models.FormasPagamento;
import br.com.fiap.tech.challengeii.parquimetrobackend.repositories.CondutorRepository;
import br.com.fiap.tech.challengeii.parquimetrobackend.repositories.FormasPagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CondutoresService {

    private final CondutoresMapper mapper;
    private final MongoTemplate mongoTemplate;
    private final CondutorRepository repository;
    private final FormasPagamentoMapper formasPagamentoMapper;
    private final FormasPagamentoRepository formasPagamentoRepository;

    public CondutoresDTO salvar(CondutoresDTO dto) {
        Optional<Condutores> condutoresOptional = this.repository.findByCpf(dto.cpf());
        if (condutoresOptional.isPresent()) {
            throw new ApplicationException("Usuário já cadastrado");
        }
        var condutor = this.mapper.toCondutores(dto);
        condutor.setId(null);

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

        if (Objects.nonNull(paginator.getParams())) {
            if (Objects.nonNull(paginator.getParams().get("nome"))) {
                query.addCriteria(Criteria.where("nome").is(paginator.getParams().get("nome")));
            }
            if (Objects.nonNull(paginator.getParams().get("cpf"))) {
                query.addCriteria(Criteria.where("cpf").is(paginator.getParams().get("cpf")));
            }
        }

        query.with(pageRequest);
        long total = this.mongoTemplate.count(query, Condutores.class);
        List<CondutoresDTO> condutores = mongoTemplate.find(query, Condutores.class)
                                                      .stream()
                                                      .map(this.mapper::toCondutoresDTO)
                                                      .toList();
        return PageableExecutionUtils.getPage(condutores, pageRequest, () -> total);
    }

    public void deletarCondutor(String id) {
        if (Objects.isNull(id) || id.isBlank()) {
            throw new ApplicationException("Parâmetro informado inválido");
        }
        var condutor = this.repository.findById(id).orElseThrow(() -> new ApplicationException("Condutor não localizado"));
        this.repository.delete(condutor);
    }

    public void cadastrarFormasPagamento(String id, List<FormasPagamentoDTO> dtos) {
        if (Objects.isNull(id) || id.isBlank()) {
            throw new ApplicationException("Parâmetro informado inválido");
        }

        var condutor = this.repository.findById(id).orElseThrow(() -> new ApplicationException("Condutor não localizado"));

        if (!dtos.isEmpty()) {

            var formasPagamentoAgrupadas = dtos.stream()
                    .map(this.formasPagamentoMapper::toFormasPagamento)
                    .distinct().collect(Collectors.groupingBy(FormasPagamento::getTipoPagamento));
            List<FormasPagamento> formasPagamentoAux;

            if (Objects.isNull(condutor.getFormasPagamentos()) || condutor.getFormasPagamentos().isEmpty()) {

                formasPagamentoAux = new ArrayList<>();

                formasPagamentoAgrupadas.forEach((tipoPagamento, formasPagamento) -> {
                    switch (tipoPagamento) {
                        case PIX -> {
                            var formasPagamentosPix = iniciarFormaPagamentoPix(tipoPagamento, formasPagamento);
                            var formasPagamentoSalvas = this.formasPagamentoRepository.saveAll(formasPagamentosPix);
                            formasPagamentoAux.addAll(formasPagamentoSalvas);
                        }
                        case CREDITO, DEBITO -> {
                            var formasPagamentosCreditoDebito = iniciarFormaPagamentoCreditoDebito(tipoPagamento, formasPagamento);
                            var formasPagamentoSalvas = this.formasPagamentoRepository.saveAll(formasPagamentosCreditoDebito);
                            formasPagamentoAux.addAll(formasPagamentoSalvas);
                        }
                    }
                });
                condutor.setFormasPagamentos(formasPagamentoAux);

                this.repository.save(condutor);

            } else {

                formasPagamentoAux = new ArrayList<>();
                List<FormasPagamento> formasPagamentoDeletar = new ArrayList<>();
                List<FormasPagamento> formasPagamentoParaAtualizar = new ArrayList<>();

                formasPagamentoAgrupadas.forEach((tipoPagamento, formasPagamento) -> {
                    switch (tipoPagamento) {
                        case PIX -> {
                            var chavesPix = condutor.getFormasPagamentos().stream().map(FormasPagamento::getChavesPix).toList();
                            var chavesNaoSalva = formasPagamento.stream()
                                    .filter(fp -> Objects.isNull(fp.getId()))
                                    .filter(fp -> chavesPix.contains(fp.getChavesPix())).toList();
                            var chavesParaAtualizar = formasPagamento.stream().filter(fp -> Objects.nonNull(fp.getId()))
                                    .filter(fp -> !chavesPix.contains(fp.getChavesPix())).toList();

                            var idsChaves = formasPagamento.stream().map(FormasPagamento::getId).distinct().toList();
                            var chavesDeletar = condutor.getFormasPagamentos().stream().filter(fp -> Objects.nonNull(fp.getId()))
                                    .filter(fp -> !idsChaves.contains(fp.getId()))
                                    .toList();
                            formasPagamentoAux.addAll(chavesNaoSalva);
                            formasPagamentoParaAtualizar.addAll(chavesParaAtualizar);
                            formasPagamentoDeletar.addAll(chavesDeletar);
                        }
//                        case CREDITO, DEBITO -> {
//                            var formasPagamentosCreditoDebito = iniciarFormaPagamentoCreditoDebito(tipoPagamento, formasPagamento);
//                            var formasPagamentoSalvas = this.formasPagamentoRepository.saveAll(formasPagamentosCreditoDebito);
//                            formasPagamentoAux.addAll(formasPagamentoSalvas);
//                        }
                    }
                });
                var formasPagamentos = this.formasPagamentoRepository.saveAll(formasPagamentoAux);
                condutor.setFormasPagamentos(formasPagamentos);
                if (!formasPagamentoParaAtualizar.isEmpty()) {
                    formasPagamentoParaAtualizar.forEach(fp -> {
                        Update update = new Update();
                        update.set("chavesPix", fp.getChavesPix());
                        Query query = new Query(Criteria.where("_id").is(fp.getId()));
                        this.mongoTemplate.updateFirst(query, update, FormasPagamento.class);
                    });
                }
                if (!formasPagamentoDeletar.isEmpty()) {
                    this.formasPagamentoRepository.deleteAll(formasPagamentoDeletar);
                }
                this.repository.save(condutor);
            }
        }
    }

    public static <T> java.util.function.Predicate<T> distinctByKey(java.util.function.Function<? super T, ?> keyExtractor) {
        java.util.Map<Object, Boolean> seen = new java.util.concurrent.ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(Objects.requireNonNull(keyExtractor.apply(t)), Boolean.TRUE) == null;
    }

    private List<FormasPagamento> iniciarFormaPagamentoPix(TipoPagamentoEnum tipoPagamento, List<FormasPagamento> formaPagamento) {
        return formaPagamento.stream().filter(f -> f.getTipoPagamento().equals(tipoPagamento))
                .map(FormasPagamento::getChavesPix).distinct()
                .map(chave -> {
                    var fp = new FormasPagamento();
                    fp.setId(null);
                    fp.setNumeroCartao(null);
                    fp.setNumeroCVC(null);
                    fp.setChavesPix(chave);
                    fp.setTipoPagamento(tipoPagamento);
                    return fp;
                }).toList();
    }

    private List<FormasPagamento> iniciarFormaPagamentoCreditoDebito(TipoPagamentoEnum tipoPagamento, List<FormasPagamento> formaPagamento) {
        return formaPagamento.stream().filter(f -> f.getTipoPagamento().equals(tipoPagamento))
                .map(FormasPagamento::getNumeroCartao).distinct()
                .map(numeroCartao -> {
                    var fp = new FormasPagamento();
                    fp.setId(null);
                    fp.setChavesPix(null);
                    fp.setNumeroCartao(numeroCartao);
                    fp.setTipoPagamento(tipoPagamento);
                    return fp;
                }).toList();
    }

    private List<FormasPagamento> iniciarListaCreditoDebito(List<FormasPagamento> formasPagamentosPix) {
        formasPagamentosPix = formasPagamentosPix.stream().peek(fp -> {
            fp.setChavesPix(null);
            fp.setId(null);
        }).toList();
        return formasPagamentosPix;
    }
}
