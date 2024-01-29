package br.com.fiap.tech.challengeii.parquimetrobackend.service;

import br.com.fiap.tech.challengeii.parquimetrobackend.controllers.exception.ApplicationException;
import br.com.fiap.tech.challengeii.parquimetrobackend.dtos.FormasPagamentoDTO;
import br.com.fiap.tech.challengeii.parquimetrobackend.enums.TipoPagamentoEnum;
import br.com.fiap.tech.challengeii.parquimetrobackend.mappers.FormasPagamentoMapper;
import br.com.fiap.tech.challengeii.parquimetrobackend.models.FormasPagamento;
import br.com.fiap.tech.challengeii.parquimetrobackend.repositories.FormasPagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FormasPagamentoService {

    private final FormasPagamentoMapper mapper;
    private final FormasPagamentoRepository repository;

    public List<FormasPagamentoDTO> salvar(List<FormasPagamentoDTO> formasPagamento) {
        for (FormasPagamentoDTO formaPagamento : formasPagamento) {
            validarFormaPagamento(formaPagamento);
        }
        return this.repository.saveAll(formasPagamento.stream().map(this.mapper::toFormasPagamento).toList())
                              .stream().map(this.mapper::toFormasPagamentoDTO).toList();
    }

    private void validarFormaPagamento(FormasPagamentoDTO formaPagamento) {
        // Se o ID estiver presente, verifica se a forma de pagamento com o ID existe
        if (Objects.nonNull(formaPagamento.id())) {
            Optional<FormasPagamento> existingFormaPagamento = this.repository.findById(formaPagamento.id());
            if (existingFormaPagamento.isEmpty()) {
                throw new ApplicationException("ID da forma de pagamento não encontrado: " + formaPagamento.id());
            }
        }
        // Verifica se já existe o número do cartão para a mesma forma de pagamento
        if (formaPagamento.tipoPagamento().equals(TipoPagamentoEnum.CREDITO) ||
            formaPagamento.tipoPagamento().equals(TipoPagamentoEnum.DEBITO)) {
            if (this.repository.existsByNumeroCartaoAndTipoPagamento(
                    formaPagamento.numeroCartao(), formaPagamento.tipoPagamento())) {
                throw new ApplicationException("Número do cartão já cadastrado para esta forma de pagamento.");
            }
        }
    }

}
