package br.com.fiap.tech.challengeii.parquimetrobackend.repository;

import br.com.fiap.tech.challengeii.parquimetrobackend.entity.Veiculos;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeiculosRepository extends MongoRepository<Veiculos, String> {

}
