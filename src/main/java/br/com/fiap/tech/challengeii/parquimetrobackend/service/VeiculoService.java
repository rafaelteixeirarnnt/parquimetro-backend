package br.com.fiap.tech.challengeii.parquimetrobackend.service;

import br.com.fiap.tech.challengeii.parquimetrobackend.dto.VeiculoDTO;
import br.com.fiap.tech.challengeii.parquimetrobackend.entity.Veiculo;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.fiap.tech.challengeii.parquimetrobackend.repository.VeiculoRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
@Service
public class VeiculoService {
    @Autowired
    private VeiculoRepository veiculoRepo;
    public Collection<Veiculo> findAll() {
        var veiculos =veiculoRepo.findAll();
        return veiculos;
    }
    public Veiculo findById(Long id) {
        Veiculo veiculo = veiculoRepo.findById(id).orElseThrow(() -> new ControllerNotFoundException("Veiculo não encontrado"));
        return veiculo;
    }
    public Veiculo save(Veiculo veiculo){
        veiculo = veiculoRepo.save(veiculo);
        return veiculo;
    }
    public Veiculo update(Long id, Veiculo veiculo) {
        try {
            Veiculo buscarVeiculo = veiculoRepo.findAll().get(id);

            buscarVeiculo.setPlaca(veiculo.getPlaca());
            buscarVeiculo.setAno(veiculo.getAno());
            buscarVeiculo.setModelo(veiculo.getModelo());
            buscarVeiculo.setCor(veiculo.getCor());

            buscarVeiculo = veiculoRepo.save(buscarVeiculo);

            return buscarVeiculo;

        } catch (EntityNotFoundException e){
            throw new ControllerNotFoundException("Veículo não encontrado");
        }
    }
    public void delete(Long id) {
        veiculoRepo.delete(id);
    }
}
