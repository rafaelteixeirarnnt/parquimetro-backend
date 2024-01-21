package br.com.fiap.tech.challengeii.parquimetrobackend.service;

import br.com.fiap.tech.challengeii.parquimetrobackend.dtos.VeiculosDTO;
import br.com.fiap.tech.challengeii.parquimetrobackend.mappers.VeiculosMapper;
import br.com.fiap.tech.challengeii.parquimetrobackend.models.Veiculos;
import br.com.fiap.tech.challengeii.parquimetrobackend.repositories.VeiculosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
@Service
@RequiredArgsConstructor
public class VeiculosService {

    private final VeiculosMapper mapper;
    private final VeiculosRepository repository;

    public Collection<Veiculos> findAll() {
        var veiculos = repository.findAll();
        return veiculos;
    }
//    public Veiculo findById(Long id) {
//        Veiculo veiculo = veiculoRepo.findById(id).orElseThrow(() -> new ControllerNotFoundException("Veiculo não encontrado"));
//        return veiculo;
//    }
    public Veiculos salvarVeiculo(VeiculosDTO dto){
        Veiculos veiculo = this.mapper.toVeiculos(dto);
        veiculo.setId(null);
        return repository.save(veiculo);
    }
//    public Veiculo update(Long id, Veiculo veiculo) {
//        try {
//            Veiculo buscarVeiculo = veiculoRepo.findAll().get(0);
//
//            buscarVeiculo.setPlaca(veiculo.getPlaca());
//            buscarVeiculo.setAno(veiculo.getAno());
//            buscarVeiculo.setModelo(veiculo.getModelo());
//            buscarVeiculo.setCor(veiculo.getCor());
//
//            buscarVeiculo = veiculoRepo.save(buscarVeiculo);
//
//            return buscarVeiculo;
//
//        } catch (Exception e){
//            throw new ControllerNotFoundException("Veículo não encontrado");
//        }
//    }
//    public void delete(Long id) {
//        veiculoRepo.deleteById(id);
//    }
}
