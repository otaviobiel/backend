package com.example.projetoveiculos.controller;

import com.example.projetoveiculos.model.Veiculo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/veiculo")
public class veiculoController {

    List<Veiculo> veiculos = new ArrayList<>();

    @GetMapping("/listar-veiculos")
    public ResponseEntity<List<Veiculo>> listarVeiculo() {
        return ResponseEntity.ok(veiculos);
    }

    @GetMapping("/listar-veiculos/{id}")
    public ResponseEntity<Veiculo> veiculo(@PathVariable int id) {
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getId() == id) {
                return ResponseEntity.ok(veiculo);
            }
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/criar-veiculo")
    public ResponseEntity<Veiculo> criarVeiculo(@RequestBody Veiculo veiculo) {
        if (veiculo == null) {
            return ResponseEntity.badRequest().build();
        }
        veiculos.add(veiculo);
        return ResponseEntity.ok(veiculo);
    }

    @PutMapping("/atualizar-veiculo/{id}")
    public ResponseEntity<Veiculo> atualizarVeiculo(
            @PathVariable int id,
            @RequestBody Veiculo dados
    ) {

        for (Veiculo veiculo : veiculos) {

            if (veiculo.getId() == id) {

                veiculo.setMarca(dados.getMarca());
                veiculo.setModelo(dados.getModelo());
                veiculo.setAno(dados.getAno());
                veiculo.setCor(dados.getCor());

                return ResponseEntity.ok(veiculo);
            }
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deletar-veiculo/{id}")
    public ResponseEntity<Void> deletarVeiculo(@PathVariable int id) {
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getId() == id) {
                veiculos.remove(veiculo);
                return ResponseEntity.noContent().build();
            }
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<Veiculo>> filtrarVeiculos(
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String modelo,
            @RequestParam(required = false) String ano
    ) {

        List<Veiculo> resultado = new ArrayList<>();

        for (Veiculo veiculo : veiculos) {

            boolean corresponde = true;

            if (marca != null && !veiculo.getMarca().equalsIgnoreCase(marca)) {
                corresponde = false;
            }

            if (modelo != null && !veiculo.getModelo().equalsIgnoreCase(modelo)) {
                corresponde = false;
            }

            if (ano != null && veiculo.getAno() != ano) {
                corresponde = false;
            }

            if (corresponde) {
                resultado.add(veiculo);
            }
        }

        return ResponseEntity.ok(resultado);
    }
}

