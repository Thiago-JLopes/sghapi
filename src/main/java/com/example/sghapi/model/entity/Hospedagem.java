package com.example.sghapi.model.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class Hospedagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String CheckInReal;
    private String CheckOutReal;
    private Float gastos;

    @ManyToOne
    private Funcionario funcionario;

    @OneToOne
    private Reserva reserva;

}
