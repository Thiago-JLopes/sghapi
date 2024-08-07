package com.example.sghapi.model.entity;

import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class Cliente extends  Pessoa{
    private String cpf;
    private String logradouro;
    private Integer numero;
    private String cep;
    private String cidade;
    private String dataNascimento;
    private String telefone;

}
