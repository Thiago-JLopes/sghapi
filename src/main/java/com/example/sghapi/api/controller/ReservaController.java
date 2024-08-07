package com.example.sghapi.api.controller;

import com.example.sghapi.api.dto.ClienteDTO;
import com.example.sghapi.api.dto.ReservaDTO;
import com.example.sghapi.model.entity.Reserva;
import com.example.sghapi.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/reservas")
@RequiredArgsConstructor
@CrossOrigin
public class ReservaController {
    private final ReservaService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Reserva> reservas = service.getReservas();
        return ResponseEntity.ok(reservas.stream().map(ReservaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Reserva> reserva = service.getReservaById(id);
        if(!reserva.isPresent()){
            return new ResponseEntity("reserva não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(reserva.map(ReservaDTO::create));
    }
}
