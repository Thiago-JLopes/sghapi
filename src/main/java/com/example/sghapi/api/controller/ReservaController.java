package com.example.sghapi.api.controller;

import com.example.sghapi.api.dto.ClienteDTO;
import com.example.sghapi.api.dto.ReservaDTO;
import com.example.sghapi.model.entity.Cliente;
import com.example.sghapi.model.entity.Quarto;
import com.example.sghapi.model.entity.Reserva;
import com.example.sghapi.service.ClienteService;
import com.example.sghapi.service.QuartoService;
import com.example.sghapi.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final ClienteService clienteService;
    private final QuartoService quartoService;

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

    public Reserva converter(ReservaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Reserva reserva = modelMapper.map(dto, Reserva.class);
        if(dto.getIdCliente()!= null){
            Optional<Cliente> cliente = clienteService.getClienteById(dto.getIdCliente());
            if(!cliente.isPresent()){
                reserva.setCliente(null);
            }else{
                reserva.setCliente(cliente.get());
            }
        }
        if(dto.getIdQuarto()!= null) {
            Optional<Quarto> quarto = quartoService.getQuartoById(dto.getIdQuarto());
            if (!quarto.isPresent()) {
                reserva.setQuarto(null);
            } else {
                reserva.setQuarto(quarto.get());
            }
        }
        return reserva;
    }
}
