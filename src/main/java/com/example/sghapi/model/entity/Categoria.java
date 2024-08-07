package com.example.sghapi.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Float preco;
    private String descricao;
    private Integer qntCamaIndividual;
    private Integer qntCamaCasal;
    private Integer comodidadeCrianca;
    private Integer qntTVAnInt;
}
