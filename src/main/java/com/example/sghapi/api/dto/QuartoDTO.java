package com.example.sghapi.api.dto;

import com.example.sghapi.model.entity.Funcionario;
import com.example.sghapi.model.entity.Quarto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuartoDTO {
    private Long id;
    private Integer numero;
    private Boolean disponibilidade;
    private Long idHotel;
    private Long idCategoria;

    public static QuartoDTO create(Quarto quarto) {
        ModelMapper modelMapper = new ModelMapper();
        QuartoDTO dto = modelMapper.map(quarto, QuartoDTO.class);
        return dto;
    }
}
