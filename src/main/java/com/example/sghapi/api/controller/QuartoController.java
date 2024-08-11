package com.example.sghapi.api.controller;

import com.example.sghapi.api.dto.ClienteDTO;
import com.example.sghapi.api.dto.QuartoDTO;
import com.example.sghapi.exception.RegraNegocioException;
import com.example.sghapi.model.entity.Categoria;
import com.example.sghapi.model.entity.Hotel;
import com.example.sghapi.model.entity.Quarto;
import com.example.sghapi.service.CategoriaService;
import com.example.sghapi.service.HotelService;
import com.example.sghapi.service.QuartoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final HotelService hotelService;
    private final CategoriaService categoriaService;

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

    @PostMapping()
    public ResponseEntity post(QuartoDTO dto) {
        try {
            Quarto quarto = converter(dto);
            quarto = service.salvar(quarto);
            return new ResponseEntity(quarto, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, QuartoDTO dto) {
        if(!service.getQuartoById(id).isPresent()){
            return new ResponseEntity("quarto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Quarto quarto = converter(dto);
            quarto.setId(id);
            service.salvar(quarto);
            return ResponseEntity.ok(quarto);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Quarto  converter(QuartoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Quarto quarto = modelMapper.map(dto, Quarto.class);
        if(dto.getIdHotel()!= null) {
            Optional<Hotel> hotel = hotelService.getHotelById(dto.getIdHotel());
            if (!hotel.isPresent()) {
                quarto.setHotel(null);
            } else {
                quarto.setHotel(hotel.get());
            }
        }
        if(dto.getIdCategoria() != null) {
            Optional<Categoria> categoria = categoriaService.getCategoriaById(dto.getIdCategoria());
            if (!categoria.isPresent()) {
                quarto.setCategoria(null);
            } else {
                quarto.setCategoria(categoria.get());
            }
        }
        return quarto;
    }
}
