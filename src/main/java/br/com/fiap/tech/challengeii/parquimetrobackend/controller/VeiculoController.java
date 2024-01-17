package br.com.fiap.tech.challengeii.parquimetrobackend.controller;

import br.com.fiap.tech.challengeii.parquimetrobackend.dto.VeiculosDTO;
import br.com.fiap.tech.challengeii.parquimetrobackend.entity.Veiculos;
import br.com.fiap.tech.challengeii.parquimetrobackend.service.VeiculosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/veiculos")
@RequiredArgsConstructor
public class VeiculoController {

    private final VeiculosService veiculoService;

    @GetMapping
    public ResponseEntity<Collection<Veiculos>> findAll() {
        var veiculos = veiculoService.findAll();
        return ResponseEntity.ok(veiculos);
    }

    //    @GetMapping("/{id}")
//    public ResponseEntity<Veiculo> findById(@PathVariable Long id) {
//        var veiculos = veiculoService.findById(id);
//        return ResponseEntity.ok(veiculos);
//    }
    @PostMapping
    public ResponseEntity<Veiculos> save(@RequestBody VeiculosDTO dto) {
        var veiculo = veiculoService.save(dto);
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(veiculo);
    }
//    @PutMapping("/{id}")
//    public ResponseEntity<Veiculo> update(@PathVariable Long id, @RequestBody Veiculo veiculo) {
//        veiculo = veiculoService.update(id, veiculo);
//        return ResponseEntity.ok(veiculo);
//    }
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        veiculoService.delete(id);
//        return ResponseEntity.noContent().build();
//    }
}
