package com.example.sghapi.api.dto;

import com.example.sghapi.model.entity.Funcionario;
import com.example.sghapi.model.entity.Reserva;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

public class ReservaDTO {
    private Long id;
    private LocalDateTime dataCheckIn;
    private LocalDateTime dataCheckOut;
    private Integer qntHospedes;
    private String status;

    public static ReservaDTO create(Reserva reserva) {
        ModelMapper modelMapper = new ModelMapper();
        ReservaDTO dto = modelMapper.map(reserva, ReservaDTO.class);
        return dto;
    }
}
