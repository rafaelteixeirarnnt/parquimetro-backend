package br.com.fiap.tech.challengeii.parquimetrobackend.service;

import br.com.fiap.tech.challengeii.parquimetrobackend.dto.VeiculosDTO;
import br.com.fiap.tech.challengeii.parquimetrobackend.entity.Veiculos;
import br.com.fiap.tech.challengeii.parquimetrobackend.repository.VeiculosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
@Service
@RequiredArgsConstructor
public class VeiculosService {

    private final VeiculosRepository veiculoRepo;

    public Collection<Veiculos> findAll() {
        var veiculos =veiculoRepo.findAll();
        return veiculos;
    }
//    public Veiculo findById(Long id) {
//        Veiculo veiculo = veiculoRepo.findById(id).orElseThrow(() -> new ControllerNotFoundException("Veiculo não encontrado"));
//        return veiculo;
//    }
    public Veiculos save(VeiculosDTO dto){
        Veiculos v = new Veiculos(null, dto.getPlaca(), dto.getAno(), dto.getModelo(), dto.getCor());
        return veiculoRepo.save(v);
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
