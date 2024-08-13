package com.example.sghapi.api.dto;

import com.example.sghapi.model.entity.Funcionario;
import com.example.sghapi.model.entity.Hospedagem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HospedagemDTO {
    private Long id;
    private LocalDateTime CheckInReal;
    private LocalDateTime CheckOutReal;
    private Float gastos;
    private Long idFuncionario;

    public static HospedagemDTO create(Hospedagem hospedagem) {
        ModelMapper modelMapper = new ModelMapper();
        HospedagemDTO dto = modelMapper.map(hospedagem, HospedagemDTO.class);
        return dto;
    }
}
