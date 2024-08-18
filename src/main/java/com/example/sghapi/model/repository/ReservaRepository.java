package com.example.sghapi.model.repository;

import com.example.sghapi.model.entity.Cliente;
import com.example.sghapi.model.entity.Quarto;
import com.example.sghapi.model.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservaRepository extends JpaRepository<Reserva,Long> {
    List<Reserva> findByQuarto(Optional<Quarto> quarto);
    List<Reserva> findByCliente(Optional<Cliente> cliente);
}
