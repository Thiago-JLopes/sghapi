package com.example.sghapi.api.dto;

import com.example.sghapi.model.entity.Cliente;
import com.example.sghapi.model.entity.Funcionario;
import org.modelmapper.ModelMapper;

public class FuncionarioDTO {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String matricula;
    private String cargo;

    public static FuncionarioDTO create(Funcionario funcionario) {
        ModelMapper modelMapper = new ModelMapper();
        FuncionarioDTO dto = modelMapper.map(funcionario, FuncionarioDTO.class);
        return dto;
    }
}
