package com.example.sghapi.api.dto;

import com.example.sghapi.model.entity.Funcionario;
import com.example.sghapi.model.entity.Hospedagem;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

public class HospedagemDTO {
    private Long id;
    private LocalDateTime CheckInReal;
    private LocalDateTime CheckOutReal;
    private Float gastos;

    public static HospedagemDTO create(Hospedagem hospedagem) {
        ModelMapper modelMapper = new ModelMapper();
        HospedagemDTO dto = modelMapper.map(hospedagem, HospedagemDTO.class);
        return dto;
    }
}
