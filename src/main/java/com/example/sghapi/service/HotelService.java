package com.example.sghapi.service;

import com.example.sghapi.model.entity.Hotel;
import com.example.sghapi.model.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelService {
    private HotelRepository repository;

    public HotelService(HotelRepository repository){
        this.repository = repository;
    }

    public List<Hotel> getHotels(){
        return repository.findAll();
    }

    public Optional<Hotel> getHotelById(Long id){
        return repository.findById(id);
    }
}
