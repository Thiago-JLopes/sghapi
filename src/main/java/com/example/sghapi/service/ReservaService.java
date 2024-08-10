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

    public void validar(Reserva reserva) {
        if(reserva.getDataCheckIn() == null) {
            throw new IllegalArgumentException("Data de check-in inválida");
        }
        if(reserva.getDataCheckOut() == null) {
            throw new IllegalArgumentException("Data de check-out inválida");
        }
        if(reserva.getQntHospedes() <= 0) {
            throw new IllegalArgumentException("Quantidade de hóspedes inválida");
        }
        if (reserva.getStatus() == null) {
            throw new IllegalArgumentException("Status inválido");
        }
        if(reserva.getQuarto() == null) {
            throw new IllegalArgumentException("Quarto inválido");
        }
        if(reserva.getCliente() == null) {
            throw new IllegalArgumentException("Cliente inválido");
        }
    }
}
