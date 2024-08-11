package com.example.sghapi.api.controller;

import com.example.sghapi.api.dto.ClienteDTO;
import com.example.sghapi.api.dto.HotelDTO;
import com.example.sghapi.exception.RegraNegocioException;
import com.example.sghapi.model.entity.Hotel;
import com.example.sghapi.service.HotelService;
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
@CrossOrigin
public class HotelController {
    private final HotelService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Hotel> hotels = service.getHotels();
        return ResponseEntity.ok(hotels.stream().map(HotelDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Hotel> hotel = service.getHotelById(id);
        if(!hotel.isPresent()){
            return new ResponseEntity("hotel não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(hotel.map(HotelDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(HotelDTO dto) {
        try {
            Hotel hotel = converter(dto);
            hotel = service.salvar(hotel);
            return new ResponseEntity(hotel, HttpStatus.CREATED);
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
