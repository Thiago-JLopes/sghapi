package com.example.sghapi.service;

import com.example.sghapi.exception.RegraNegocioException;
import com.example.sghapi.model.entity.Hotel;
import com.example.sghapi.model.repository.HotelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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

    @Transactional
    public Hotel salvar(Hotel hotel){
        validar(hotel);
        return repository.save(hotel);
    }

    @Transactional
    public void excluir(Hotel hotel){
        Objects.requireNonNull(hotel.getId());
        repository.delete(hotel);
    }

    public void validar(Hotel hotel) {
        if(hotel.getNome() == null || hotel.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if(hotel.getNumero() == null || hotel.getNumero() < 0) {
            throw new RegraNegocioException("Número inválido");
        }
        if(hotel.getCep() == null || hotel.getCep().trim().equals("")) {
            throw new RegraNegocioException("CEP inválido");
        }
    }
}
