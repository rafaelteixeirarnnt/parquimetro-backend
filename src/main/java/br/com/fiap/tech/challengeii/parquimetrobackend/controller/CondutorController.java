package br.com.fiap.tech.challengeii.parquimetrobackend.controller;

import br.com.fiap.tech.challengeii.parquimetrobackend.dto.CondutorDTO;
import br.com.fiap.tech.challengeii.parquimetrobackend.service.CondutorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/condutor")
public class CondutorController {
    private final CondutorService condutorService;

    @Autowired
    public CondutorController(CondutorService condutorService){
        this.condutorService = condutorService;
    }

    @GetMapping("/obterTodos")
    public ResponseEntity<Page<CondutorDTO>> findAll(
            @PageableDefault(size = 10, page = 0, sort = "nome")Pageable pageable) {
        Page<CondutorDTO> condutorDTO = condutorService.findAll(pageable);

        return ResponseEntity.ok(condutorDTO);
    }

    @GetMapping
    public ResponseEntity<CondutorDTO> findById(@PathVariable Long id){
        CondutorDTO condutorDTO = condutorService.findById(id);
        return ResponseEntity.ok(condutorDTO);
    }

    @PostMapping
    public ResponseEntity<CondutorDTO> save(@Valid @RequestBody CondutorDTO condutorDTO) {
        CondutorDTO savedCondutor = condutorService.save(condutorDTO);
        return new ResponseEntity<>(savedCondutor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CondutorDTO> update(@PathVariable Long id, @RequestBody CondutorDTO condutorDTO) {
        CondutorDTO updatedCondutor = condutorService.update(id, condutorDTO);
        return ResponseEntity.ok(updatedCondutor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        condutorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
