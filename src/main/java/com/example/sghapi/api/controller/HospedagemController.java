package com.example.sghapi.api.controller;

import com.example.sghapi.api.dto.ClienteDTO;
import com.example.sghapi.api.dto.HospedagemDTO;
import com.example.sghapi.model.entity.Cliente;
import com.example.sghapi.model.entity.Hospedagem;
import com.example.sghapi.service.ClienteService;
import com.example.sghapi.service.HospedagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/hospedagens")
@RequiredArgsConstructor
@CrossOrigin
public class HospedagemController {
    private final HospedagemService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Hospedagem> hospedagems = service.getHospedagens();
        return ResponseEntity.ok(hospedagems.stream().map(HospedagemDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Hospedagem> hospedagem = service.getHospedagemById(id);
        if(!hospedagem.isPresent()){
            return new ResponseEntity("hospedagem não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(hospedagem.map(HospedagemDTO::create));
    }
}
