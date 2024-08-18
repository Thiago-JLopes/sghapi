package com.example.sghapi.api.controller;

import com.example.sghapi.api.dto.ClienteDTO;
import com.example.sghapi.api.dto.ReservaDTO;
import com.example.sghapi.exception.RegraNegocioException;
import com.example.sghapi.model.entity.Cliente;
import com.example.sghapi.model.entity.Reserva;
import com.example.sghapi.service.ClienteService;
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
@RequestMapping("api/v1/clientes")
@RequiredArgsConstructor
@Api("Api de Clientes")
@CrossOrigin
public class ClienteController {
    private final ClienteService service;
    private final ReservaService reservaService;

    @GetMapping()
    @ApiOperation("Obter todos os clientes")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Todos clientes retornados com sucesso"),
            @ApiResponse(code = 204, message = "Nenhum cliente encontrado")
    })
    public ResponseEntity get() {
        List<Cliente> clientes = service.getClientes();
        return ResponseEntity.ok(clientes.stream().map(ClienteDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente encontrado"),
            @ApiResponse(code = 404, message = "Cliente não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do cliente")Long id) {
        Optional<Cliente> cliente = service.getClienteById(id);
        if(!cliente.isPresent()){
            return new ResponseEntity("cliente não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cliente.map(ClienteDTO::create));
    }

    @GetMapping("{id}/reservas")
    @ApiOperation("Obter todas as reservas de um cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Reservas retornadas com sucesso"),
            @ApiResponse(code = 404, message = "Cliente não encontrado")
    })
    public ResponseEntity getQuartos(@PathVariable("id") Long id) {
        Optional<Cliente> cliente = service.getClienteById(id);
        if(!cliente.isPresent()){
            return new ResponseEntity("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
        List<Reserva> reservas = reservaService.getReservasByCliente(cliente);
        return ResponseEntity.ok(reservas.stream().map(ReservaDTO::create).collect(Collectors.toList()));
    }

    @PostMapping()
    @ApiOperation("Salvar dados de um cliente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cliente salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar cliente")
    })
    public ResponseEntity post(@RequestBody ClienteDTO dto) {
        try {
            Cliente cliente = converter(dto);
            cliente = service.salvar(cliente);
            return new ResponseEntity(cliente, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualizar um cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Cliente não encontrado"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 409, message = "Conflito na requisição")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ClienteDTO dto) {
        if (!service.getClienteById(id).isPresent()) {
            return new ResponseEntity("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Cliente cliente = converter(dto);
            cliente.setId(id);
            service.salvar(cliente);
            return ResponseEntity.ok(cliente);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir uma cliente")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cliente excluído com sucesso"),
            @ApiResponse(code = 404, message = "Cliente não encontrado"),
            @ApiResponse(code = 400, message = "Requisição inválida")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Cliente> cliente = service.getClienteById(id);
        if (!cliente.isPresent()) {
            return new ResponseEntity("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(cliente.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Cliente converter(ClienteDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Cliente.class);
    }
}
