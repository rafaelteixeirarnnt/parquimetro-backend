package br.com.fiap.tech.challengeii.parquimetrobackend.repositories;

import br.com.fiap.tech.challengeii.parquimetrobackend.models.Condutores;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CondutorRepository extends MongoRepository<Condutores, String> {

}
