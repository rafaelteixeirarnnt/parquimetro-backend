package br.com.fiap.tech.challengeii.parquimetrobackend.repositories;

import br.com.fiap.tech.challengeii.parquimetrobackend.models.Veiculos;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeiculosRepository extends MongoRepository<Veiculos, String> {

}
