package com.example.sghapi.api.dto;

import com.example.sghapi.model.entity.Funcionario;
import com.example.sghapi.model.entity.Hotel;
import org.modelmapper.ModelMapper;

public class HotelDTO {
    private Long id;
    private String nome;
    private Float classificacao;
    private Integer numero;
    private String cep;
    private String logradouro;
    private String cidade;

    public static HotelDTO create(Hotel hotel) {
        ModelMapper modelMapper = new ModelMapper();
        HotelDTO dto = modelMapper.map(hotel, HotelDTO.class);
        return dto;
    }
}
