package br.com.fiap.tech.challengeii.parquimetrobackend.repositories;

import br.com.fiap.tech.challengeii.parquimetrobackend.models.Veiculos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VeiculosRepository extends MongoRepository<Veiculos, String> {

    Optional<Veiculos> findByPlacaAndModeloAndCondutor_Id(String placa, String modelo, String idCondutor);

    Page<Veiculos> findByPlacaEqualsOrAnoEqualsOrModeloOrCorEquals(String placa, String ano, String modelo, String cor, Pageable pageable);


}
