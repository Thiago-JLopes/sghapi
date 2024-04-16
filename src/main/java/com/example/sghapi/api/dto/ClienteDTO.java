package com.example.sghapi.api.dto;

import com.example.sghapi.model.entity.Cliente;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private String logradouro;
    private Integer numero;
    private String cep;
    private String cidade;
    private LocalDate dataNascimento;
    private String telefone;

    public static ClienteDTO create(Cliente cliente) {
        ModelMapper modelMapper = new ModelMapper();
        ClienteDTO dto = modelMapper.map(cliente, ClienteDTO.class);
        return dto;
    }
}
