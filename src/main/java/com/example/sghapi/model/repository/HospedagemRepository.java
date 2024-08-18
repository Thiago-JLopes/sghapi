package com.example.sghapi.model.repository;

import com.example.sghapi.model.entity.Funcionario;
import com.example.sghapi.model.entity.Hospedagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HospedagemRepository extends JpaRepository<Hospedagem, Long> {
    List<Hospedagem> findByFuncionario(Optional<Funcionario> funcionario);
}
