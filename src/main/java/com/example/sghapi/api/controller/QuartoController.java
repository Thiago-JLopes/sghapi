package com.example.sghapi.api.controller;

import com.example.sghapi.api.dto.ClienteDTO;
import com.example.sghapi.api.dto.QuartoDTO;
import com.example.sghapi.model.entity.Hotel;
import com.example.sghapi.model.entity.Quarto;
import com.example.sghapi.service.QuartoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/quartos")
@RequiredArgsConstructor
@CrossOrigin
public class QuartoController {
    private final QuartoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Quarto> quartos = service.getQuartos();
        return ResponseEntity.ok(quartos.stream().map(QuartoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Quarto> quarto = service.getQuartoById(id);
        if(!quarto.isPresent()){
            return new ResponseEntity("quarto não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(quarto.map(QuartoDTO::create));
    }
}
