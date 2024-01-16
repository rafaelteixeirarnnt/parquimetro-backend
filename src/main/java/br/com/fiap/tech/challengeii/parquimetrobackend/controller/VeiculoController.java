package br.com.fiap.tech.challengeii.parquimetrobackend.controller;

import br.com.fiap.tech.challengeii.parquimetrobackend.entity.Veiculo;
import br.com.fiap.tech.challengeii.parquimetrobackend.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
@RestController
@RequestMapping("/veiculos")
public class VeiculoController {
    @Autowired
    private VeiculoService veiculoService;
    @GetMapping
    public ResponseEntity<Collection<Veiculo>> findAll() {
        var veiculos = veiculoService.findAll();
        return ResponseEntity.ok(veiculos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Veiculo> findById(@PathVariable Long id) {
        var veiculos = veiculoService.findById(id);
        return ResponseEntity.ok(veiculos);
    }
    @PostMapping
    public ResponseEntity<Veiculo> save(@RequestBody Veiculo veiculo) {
        veiculo = veiculoService.save(veiculo);
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(veiculo);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Veiculo> update(@PathVariable Long id, @RequestBody Veiculo veiculo) {
        veiculo = veiculoService.update(id, veiculo);
        return ResponseEntity.ok(veiculo);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        veiculoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
