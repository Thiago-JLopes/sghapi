package com.example.sghapi.api.dto;

import com.example.sghapi.model.entity.Reserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDTO {
    private Long id;
    private String dataCheckIn;
    private String dataCheckOut;
    private Integer qntHospedes;
    private String status;
    private Long idQuarto;
    private Long idCliente;

    public static ReservaDTO create(Reserva reserva) {
        ModelMapper modelMapper = new ModelMapper();
        ReservaDTO dto = modelMapper.map(reserva, ReservaDTO.class);
        return dto;
    }
}
