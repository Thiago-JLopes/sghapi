package com.example.sghapi.api.controller;

import com.example.sghapi.api.dto.ClienteDTO;
import com.example.sghapi.api.dto.ReservaDTO;
import com.example.sghapi.exception.RegraNegocioException;
import com.example.sghapi.model.entity.Cliente;
import com.example.sghapi.model.entity.Quarto;
import com.example.sghapi.model.entity.Reserva;
import com.example.sghapi.service.ClienteService;
import com.example.sghapi.service.QuartoService;
import com.example.sghapi.service.ReservaService;
import io.swagger.annotations.*;
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
@Api("Api de reservas")
@CrossOrigin
public class ReservaController {
    private final ReservaService service;
    private final ClienteService clienteService;
    private final QuartoService quartoService;

    @GetMapping()
    @ApiOperation("Obter todas as reservas")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Todas reservas retornadas com sucesso"),
            @ApiResponse(code = 204, message = "Nenhuma reserva encontrada")
    })
    public ResponseEntity get() {
        List<Reserva> reservas = service.getReservas();
        return ResponseEntity.ok(reservas.stream().map(ReservaDTO::create).collect(Collectors.toList()));
    }


    @GetMapping("/{id}")
    @ApiOperation("Obter dados de uma Reserva")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Reserva encontrada"),
            @ApiResponse(code = 404, message = "Reserva não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id da reserva") Long id) {
        Optional<Reserva> reserva = service.getReservaById(id);
        if(!reserva.isPresent()){
            return new ResponseEntity("reserva não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(reserva.map(ReservaDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salvar dados de uma Reserva")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Reserva salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar Reserva")
    })
    public ResponseEntity post(@RequestBody ReservaDTO dto) {
        try {
            Reserva reserva = converter(dto);
            reserva = service.salvar(reserva);
            return new ResponseEntity(reserva, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualizar dados de uma Reserva")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Reserva atualizada com sucesso"),
            @ApiResponse(code = 404, message = "Reserva não encontrada"),
            @ApiResponse(code = 400, message = "Erro ao atualizar Reserva")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ReservaDTO dto) {
        if(!service.getReservaById(id).isPresent()){
            return new ResponseEntity("reserva não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Reserva reserva = converter(dto);
            reserva.setId(id);
            service.salvar(reserva);
            return ResponseEntity.ok(reserva);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir dados de uma Reserva")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Reserva excluída com sucesso"),
            @ApiResponse(code = 404, message = "Reserva não encontrada"),
            @ApiResponse(code = 400, message = "Erro ao excluir Reserva")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Reserva> reserva = service.getReservaById(id);
        if(!reserva.isPresent()){
            return new ResponseEntity("reserva não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(reserva.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
