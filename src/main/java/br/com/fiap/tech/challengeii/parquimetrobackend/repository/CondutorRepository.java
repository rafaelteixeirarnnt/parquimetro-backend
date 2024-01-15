package br.com.fiap.tech.challengeii.parquimetrobackend.repository;

import br.com.fiap.tech.challengeii.parquimetrobackend.entity.Condutor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CondutorRepository extends MongoRepository<Condutor, Long>{

}
