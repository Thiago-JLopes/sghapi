package com.example.sghapi.service;

import com.example.sghapi.model.entity.Categoria;
import com.example.sghapi.model.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    private CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository){
        this.repository = repository;
    }

    public List<Categoria> getCategorias(){
        return repository.findAll();
    }

    public Optional<Categoria> getCategoriaById(Long id){
        return repository.findById(id);
    }
}
