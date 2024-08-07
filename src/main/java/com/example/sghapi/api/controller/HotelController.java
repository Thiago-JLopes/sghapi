package com.example.sghapi.api.controller;

import com.example.sghapi.api.dto.ClienteDTO;
import com.example.sghapi.api.dto.HotelDTO;
import com.example.sghapi.model.entity.Cliente;
import com.example.sghapi.model.entity.Hotel;
import com.example.sghapi.service.ClienteService;
import com.example.sghapi.service.HotelService;
import lombok.RequiredArgsConstructor;
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
}
