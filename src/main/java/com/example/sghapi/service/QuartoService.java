package com.example.sghapi.service;

import com.example.sghapi.exception.RegraNegocioException;
import com.example.sghapi.model.entity.Quarto;
import com.example.sghapi.model.repository.QuartoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class QuartoService {
    private QuartoRepository repository;

    public QuartoService(QuartoRepository repository){
        this.repository = repository;
    }

    public List<Quarto> getQuartos(){
        return repository.findAll();
    }

    public Optional<Quarto> getQuartoById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Quarto salvar(Quarto quarto){
        return repository.save(quarto);
    }

    @Transactional
    public void excluir(Quarto quarto){
        repository.delete(quarto);
    }
}
