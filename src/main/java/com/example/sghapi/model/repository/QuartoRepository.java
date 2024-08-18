package com.example.sghapi.model.repository;

import com.example.sghapi.model.entity.Categoria;
import com.example.sghapi.model.entity.Quarto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuartoRepository extends JpaRepository<Quarto,Long> {
    List<Quarto> findByCategoria(Optional<Categoria> categoria);
}
