package com.example.sghapi.api.controller;

import com.example.sghapi.api.dto.ClienteDTO;
import com.example.sghapi.api.dto.HospedagemDTO;
import com.example.sghapi.exception.RegraNegocioException;
import com.example.sghapi.model.entity.Cliente;
import com.example.sghapi.model.entity.Funcionario;
import com.example.sghapi.model.entity.Hospedagem;
import com.example.sghapi.model.entity.Reserva;
import com.example.sghapi.service.ClienteService;
import com.example.sghapi.service.FuncionarioService;
import com.example.sghapi.service.HospedagemService;
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
@RequestMapping("api/v1/hospedagens")
@RequiredArgsConstructor
@CrossOrigin
public class HospedagemController {
    private final HospedagemService service;
    private final FuncionarioService funcionarioService;
    private final ReservaService reservaService;

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

    @PostMapping()
    public ResponseEntity post(HospedagemDTO dto){
        try{
            Hospedagem hospedagem = converter(dto);
            Reserva reserva = reservaService.salvar(hospedagem.getReserva());
            hospedagem.setReserva(reserva);
            hospedagem = service.salvar(hospedagem);
            return new ResponseEntity(hospedagem, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, HospedagemDTO dto){
        if(!service.getHospedagemById(id).isPresent()){
            return new ResponseEntity("Hospedagem não encontrada", HttpStatus.NOT_FOUND);
        }
        try{
            Hospedagem hospedagem = converter(dto);
            hospedagem.setId(id);
            Reserva reserva = reservaService.salvar(hospedagem.getReserva());
            hospedagem.setReserva(reserva);
            service.salvar(hospedagem);
            return ResponseEntity.ok(hospedagem);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id){
        Optional<Hospedagem> hospedagem = service.getHospedagemById(id);
        if(!hospedagem.isPresent()){
            return new ResponseEntity("Hospedagem não encontrada", HttpStatus.NOT_FOUND);
        }
        try{
            service.excluir(hospedagem.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }catch(RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Hospedagem converter(HospedagemDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Hospedagem hospedagem = modelMapper.map(dto, Hospedagem.class);
        Reserva reserva = modelMapper.map(dto, Reserva.class);
        hospedagem.setReserva(reserva);
        if(dto.getIdFuncionario() != null){
            Optional<Funcionario> funcionario = funcionarioService.getFuncionarioById(dto.getIdFuncionario());
            if(!funcionario.isPresent()){
                hospedagem.setFuncionario(null);
            }else{
                hospedagem.setFuncionario(funcionario.get());
            }
        }
        return  hospedagem;
    }
}
