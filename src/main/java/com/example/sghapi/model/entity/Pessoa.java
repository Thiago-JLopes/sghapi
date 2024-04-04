package com.example.sghapi.model.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class Pessoa {
    private String nome;
    private String email;
    private String senha;

}
