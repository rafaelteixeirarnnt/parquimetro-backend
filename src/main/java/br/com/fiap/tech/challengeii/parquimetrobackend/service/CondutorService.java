package br.com.fiap.tech.challengeii.parquimetrobackend.service;

import br.com.fiap.tech.challengeii.parquimetrobackend.controller.exception.ControllerNotFoundException;
import br.com.fiap.tech.challengeii.parquimetrobackend.dto.CondutorDTO;
import br.com.fiap.tech.challengeii.parquimetrobackend.entity.Condutor;
import br.com.fiap.tech.challengeii.parquimetrobackend.repository.CondutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CondutorService {
    private final CondutorRepository condutorRepository;

//    @Autowired
//    public CondutorService(CondutorRepository condutorRepository){
//        this.condutorRepository = condutorRepository;
//    }

    public Page<CondutorDTO> findAll(Pageable pageable) {
        Page<Condutor> condutores = condutorRepository.findAll(pageable);
        return condutores.map(this::toDTO);
    }

    public CondutorDTO findById(Long id) {
        Condutor condutor = condutorRepository.findById(id).orElseThrow(() ->
                new ControllerNotFoundException("Condutor não encontrado")); //criar as exceptions
        return toDTO(condutor);
    }

    public CondutorDTO save(CondutorDTO condutorDTO){
        Condutor condutor = toEntity(condutorDTO);
        condutor = condutorRepository.save(condutor);
        return toDTO(condutor);
    }

    public void delete(Long id) {
        condutorRepository.deleteById(id);
    }

    public CondutorDTO update(Long id, CondutorDTO condutorDTO){
        try{
            Condutor condutor = condutorRepository.findById(id).get();
            condutor.setNome(condutorDTO.nome());
            condutor.setEndereco(condutorDTO.endereco());
            condutor.setEmail(condutorDTO.email());
            condutor.setNumCelular(condutorDTO.numCelular());

            condutorRepository.save(condutor);
            return toDTO(condutor);
        } catch (Exception e) {
            throw new ControllerNotFoundException("Usuário Não encontrado!!!!!!");
        }
    }

    private CondutorDTO toDTO(Condutor condutor){
        return new CondutorDTO(
                condutor.getId(),
                condutor.getNome(),
                condutor.getEndereco(),
                condutor.getEmail(),
                condutor.getNumCelular()
        );
    }

    private Condutor toEntity(CondutorDTO condutorDTO) {
        return new Condutor(
                condutorDTO.id(),
                condutorDTO.nome(),
                condutorDTO.endereco(),
                condutorDTO.email(),
                condutorDTO.numCelular()
        );
    }
}
