package com.example.sghapi.api.controller;

import com.example.sghapi.api.dto.CategoriaDTO;
import com.example.sghapi.api.dto.ClienteDTO;
import com.example.sghapi.model.entity.Categoria;
import com.example.sghapi.model.entity.Cliente;
import com.example.sghapi.service.CategoriaService;
import com.example.sghapi.service.ClienteService;
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
@CrossOrigin
public class CategoriaController {
    private final CategoriaService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Categoria> categorias = service.getCategorias();
        return ResponseEntity.ok(categorias.stream().map(CategoriaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Categoria> categoria = service.getCategoriaById(id);
        if(!categoria.isPresent()){
            return new ResponseEntity("categoria não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(categoria.map(CategoriaDTO::create));
    }

}
