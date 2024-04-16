package com.example.sghapi.api.dto;

import com.example.sghapi.model.entity.Funcionario;
import com.example.sghapi.model.entity.Quarto;
import org.modelmapper.ModelMapper;

public class QuartoDTO {
    private Long id;
    private Integer numero;
    private Boolean disponibilidade;

    public static QuartoDTO create(Quarto quarto) {
        ModelMapper modelMapper = new ModelMapper();
        QuartoDTO dto = modelMapper.map(quarto, QuartoDTO.class);
        return dto;
    }
}
