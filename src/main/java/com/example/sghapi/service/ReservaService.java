package com.example.sghapi.service;

import com.example.sghapi.model.entity.Reserva;
import com.example.sghapi.model.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReservaService {
    private ReservaRepository repository;

    public ReservaService(ReservaRepository repository){
        this.repository = repository;
    }

    public List<Reserva> getReservas(){
        return repository.findAll();
    }

    public Optional<Reserva> getReservaById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Reserva salvar(Reserva reserva){
        return repository.save(reserva);
    }

    @Transactional
    public void excluir(Reserva reserva){
        Objects.requireNonNull(reserva.getId());
        repository.delete(reserva);
    }
}
