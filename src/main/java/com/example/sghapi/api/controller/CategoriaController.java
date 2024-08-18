package com.example.sghapi.api.controller;

import com.example.sghapi.api.dto.CategoriaDTO;
import com.example.sghapi.api.dto.QuartoDTO;
import com.example.sghapi.exception.RegraNegocioException;
import com.example.sghapi.model.entity.Categoria;
import com.example.sghapi.model.entity.Quarto;
import com.example.sghapi.service.CategoriaService;
import com.example.sghapi.service.QuartoService;
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
@RequestMapping("api/v1/categorias")
@RequiredArgsConstructor
@Api("Api de Categorias")
@CrossOrigin
public class CategoriaController {
    private final CategoriaService service;
    private final QuartoService quartoService;

    @GetMapping()
    @ApiOperation("Obter todas as categorias")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Todas categorias retornadas com sucesso"),
            @ApiResponse(code = 204, message = "Nenhuma categoria encontrada")
    })
    public ResponseEntity get() {
        List<Categoria> categorias = service.getCategorias();
        return ResponseEntity.ok(categorias.stream().map(CategoriaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter categoria")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Categoria encontrada"),
            @ApiResponse(code = 404, message = "Categoria não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id da categoria") Long id) {
        Optional<Categoria> categoria = service.getCategoriaById(id);
        if(!categoria.isPresent()){
            return new ResponseEntity("categoria não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(categoria.map(CategoriaDTO::create));
    }

    @GetMapping("{id}/quartos")
    @ApiOperation("Obter todos os quartos de uma categoria")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Quartos retornados com sucesso"),
            @ApiResponse(code = 404, message = "Categoria não encontrada")
    })
    public ResponseEntity getQuartos(@PathVariable("id") @ApiParam("Id da categoria")Long id) {
        Optional<Categoria> categoria = service.getCategoriaById(id);
        if(!categoria.isPresent()){
            return new ResponseEntity("Categoria não encontrada", HttpStatus.NOT_FOUND);
        }
        List<Quarto> quartos = quartoService.getQuartosByCategoria(categoria);
        return ResponseEntity.ok(quartos.stream().map(QuartoDTO::create).collect(Collectors.toList()));
    }

    @PostMapping()
    @ApiOperation("Salvar dados de uma categoria")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Categoria salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar categoria")
    })
    public ResponseEntity post(@RequestBody CategoriaDTO dto) {
        try {
            Categoria categoria = converter(dto);
            categoria = service.salvar(categoria);
            return new ResponseEntity(categoria, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualizar uma categoria")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Categoria atualizada com sucesso"),
            @ApiResponse(code = 404, message = "Categoria não encontrada"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 409, message = "Conflito na requisição")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody CategoriaDTO dto) {
        if (!service.getCategoriaById(id).isPresent()) {
            return new ResponseEntity("Categoria não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Categoria categoria = converter(dto);
            categoria.setId(id);
            service.salvar(categoria);
            return ResponseEntity.ok(categoria);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir uma categoria")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Categoria excluída com sucesso"),
            @ApiResponse(code = 404, message = "Categoria não encontrada"),
            @ApiResponse(code = 400, message = "Requisição inválida")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Categoria> categoria = service.getCategoriaById(id);
        if (!categoria.isPresent()) {
            return new ResponseEntity("Categoria não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(categoria.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Categoria converter(CategoriaDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Categoria.class);

    }

}
