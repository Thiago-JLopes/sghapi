package com.example.sghapi.api.controller;

import com.example.sghapi.api.dto.FuncionarioDTO;
import com.example.sghapi.api.dto.HospedagemDTO;
import com.example.sghapi.exception.RegraNegocioException;
import com.example.sghapi.model.entity.Funcionario;
import com.example.sghapi.model.entity.Hospedagem;
import com.example.sghapi.service.FuncionarioService;
import com.example.sghapi.service.HospedagemService;
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
@RequestMapping("api/v1/funcionarios")
@RequiredArgsConstructor
@Api("Api de Funcionarios")
@CrossOrigin
public class FuncionarioController {
    private final FuncionarioService service;
    private final HospedagemService hospedagemService;

    @GetMapping()
    @ApiOperation("Obter todos os funcionarios")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Todos funcionarios retornados com sucesso"),
            @ApiResponse(code = 204, message = "Nenhum funcionario encontrado")
    })
    public ResponseEntity get() {
        List<Funcionario> funcionarios = service.getFuncionarios();
        return ResponseEntity.ok(funcionarios.stream().map(FuncionarioDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter funcionario")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Funcionario encontrado"),
            @ApiResponse(code = 404, message = "Funcionario não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Funcionario> funcionario = service.getFuncionarioById(id);
        if(!funcionario.isPresent()){
            return new ResponseEntity("funcionario não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(funcionario.map(FuncionarioDTO::create));
    }

    @GetMapping("{id}/hospedagens")
    @ApiOperation("Obter todas as hospedagens de um funcionario")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Hospedagens retornadas com sucesso"),
            @ApiResponse(code = 404, message = "Funcionario não encontrado")
    })
    public ResponseEntity getHospedagens(@PathVariable("id") @ApiParam("Id do funcionario") Long id){
        Optional<Funcionario> funcionario = service.getFuncionarioById(id);
        if(!funcionario.isPresent()){
            return new ResponseEntity("Funcionario não encontrado", HttpStatus.NOT_FOUND);
        }
        List<Hospedagem> hospedagems = hospedagemService.getHospedagensByFuncionario(funcionario);
        return ResponseEntity.ok(hospedagems.stream().map(HospedagemDTO::create).collect(Collectors.toList()));
    }

    @PostMapping()
    @ApiOperation("Salvar dados de um funcionario")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Funcionario salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar funcionario")
    })
    public ResponseEntity post(@RequestBody FuncionarioDTO dto) {
        try {
            Funcionario funcionario = converter(dto);
            funcionario = service.salvar(funcionario);
            return new ResponseEntity(funcionario, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualizar um funcionario")
    @ApiResponses({
            @ApiResponse(code = 200, message = "funcionario atualizado com sucesso"),
            @ApiResponse(code = 404, message = "funcionario não encontrado"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 409, message = "Conflito na requisição")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody FuncionarioDTO dto) {
        if (!service.getFuncionarioById(id).isPresent()) {
            return new ResponseEntity("Funcionario não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Funcionario funcionario = converter(dto);
            funcionario.setId(id);
            service.salvar(funcionario);
            return ResponseEntity.ok(funcionario);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir um funcionario")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Funcionario excluído com sucesso"),
            @ApiResponse(code = 404, message = "Funcionario não encontrado"),
            @ApiResponse(code = 400, message = "Requisição inválida")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Funcionario> funcionario = service.getFuncionarioById(id);
        if (!funcionario.isPresent()) {
            return new ResponseEntity("Funcionario não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(funcionario.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Funcionario converter(FuncionarioDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Funcionario.class);
    }
}