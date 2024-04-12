package com.example.sghapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataCheckIn;
    private LocalDateTime dataCheckOut;
    private Integer qntHospedes;
    private String status;

    @ManyToOne
    private Quarto quarto;

    @ManyToOne
    private Cliente cliente;
}
