package com.example.sghapi.model.entity;

import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class Funcionario extends Pessoa{
    private String matricula;
    private String cargo;

}
