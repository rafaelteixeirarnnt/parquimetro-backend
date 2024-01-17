package br.com.fiap.tech.challengeii.parquimetrobackend.repository;

import br.com.fiap.tech.challengeii.parquimetrobackend.entity.Veiculo;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface VeiculoRepository extends MongoRepository<Veiculo, String> {

}
