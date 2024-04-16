package com.example.sghapi.api.dto;


import com.example.sghapi.model.entity.Categoria;
import com.example.sghapi.model.entity.Funcionario;
import org.modelmapper.ModelMapper;

public class CategoriaDTO {
    private Long id;
    private float preco;
    private String descricao;
    private Integer qntCamaIndividual;
    private Integer qntCamaCasal;
    private Integer comodidadeCrianca;
    private Integer qntTVAnInt;

    public static CategoriaDTO create(Categoria categoria) {
        ModelMapper modelMapper = new ModelMapper();
        CategoriaDTO dto = modelMapper.map(categoria, CategoriaDTO.class);
        return dto;
    }
}
