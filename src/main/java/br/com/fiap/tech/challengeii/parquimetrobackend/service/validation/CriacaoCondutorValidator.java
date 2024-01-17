/*package br.com.fiap.tech.challengeii.parquimetrobackend.service.validation;

import br.com.fiap.tech.challengeii.parquimetrobackend.dto.CondutorDTO;
import br.com.fiap.tech.challengeii.parquimetrobackend.entity.Condutor;
import br.com.fiap.tech.challengeii.parquimetrobackend.repository.CondutorRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
public class CriacaoCondutorValidator implements ConstraintValidator<CriacaoCondutorValid, CondutorDTO> {
    @Autowired
    private CondutorRepository rep;

    @Override
    public void initialize(CriacaoCondutorValid anotation) {}

    @Override
    public boolean isValid(CondutorDTO dto, ConstraintValidatorContext context) {
        Optional<Condutor> usuario = rep.findByEmail(dto.email());
        return usuario.isEmpty();
    }
}*/