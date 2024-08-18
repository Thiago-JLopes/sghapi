package com.example.sghapi.api.controller;

import com.example.sghapi.api.dto.ClienteDTO;
import com.example.sghapi.api.dto.HotelDTO;
import com.example.sghapi.exception.RegraNegocioException;
import com.example.sghapi.model.entity.Hotel;
import com.example.sghapi.service.HotelService;
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
@RequestMapping("api/v1/hoteis")
@RequiredArgsConstructor
@Api("Api de Hoteis")
@CrossOrigin
public class HotelController {
    private final HotelService service;

    @GetMapping()
    @ApiOperation("Obter todos os hoteis")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Todos hoteis retornados com sucesso"),
            @ApiResponse(code = 204, message = "Nenhum hotel encontrado")
    })
    public ResponseEntity get() {
        List<Hotel> hotels = service.getHotels();
        return ResponseEntity.ok(hotels.stream().map(HotelDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter hotel")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Hotel encontrado"),
            @ApiResponse(code = 404, message = "Hotel não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do hotel")Long id) {
        Optional<Hotel> hotel = service.getHotelById(id);
        if(!hotel.isPresent()){
            return new ResponseEntity("hotel não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(hotel.map(HotelDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salvar dados de um hotel")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Hotel salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar hotel")
    })
    public ResponseEntity post(@RequestBody HotelDTO dto) {
        try {
            Hotel hotel = converter(dto);
            hotel = service.salvar(hotel);
            return new ResponseEntity(hotel, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualizar dados de um hotel")
    @ApiResponses({
            @ApiResponse(code = 202, message = "Hotel atualizado com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao atualizar hotel"),
            @ApiResponse(code = 404, message = "Hotel não encontrado")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody HotelDTO dto) {
        if(!service.getHotelById(id).isPresent()){
            return new ResponseEntity("hotel não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Hotel hotel = converter(dto);
            hotel.setId(id);
            service.salvar(hotel);
            return ResponseEntity.ok(hotel);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir hotel")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Hotel excluído com sucesso"),
            @ApiResponse(code = 404, message = "Hotel não encontrado"),
            @ApiResponse(code = 400, message = "Erro ao excluir hotel")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Hotel> hotel = service.getHotelById(id);
        if(!hotel.isPresent()){
            return new ResponseEntity("hotel não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(hotel.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public Hotel converter(HotelDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Hotel hotel = modelMapper.map(dto, Hotel.class);
        return hotel;
    }
}
