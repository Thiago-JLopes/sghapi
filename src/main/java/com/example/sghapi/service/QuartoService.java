package com.example.sghapi.service;

import com.example.sghapi.exception.RegraNegocioException;
import com.example.sghapi.model.entity.Categoria;
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

    public List<Quarto> getQuartosByCategoria(Optional<Categoria> categoria){
        return repository.findByCategoria(categoria);
    }

    @Transactional
    public Quarto salvar(Quarto quarto){
        validar(quarto);
        return repository.save(quarto);
    }

    @Transactional
    public void excluir(Quarto quarto){
        repository.delete(quarto);
    }

    public void validar(Quarto quarto){
        if(quarto.getNumero() == null || quarto.getNumero() < 0){
            throw new RegraNegocioException("Número inválido");
        }
        if(quarto.getDisponibilidade() == null){
            throw new RegraNegocioException("Disponibilidade inválida");
        }
        if(quarto.getCategoria() == null){
            throw new RegraNegocioException("Categoria inválida");
        }
        if (quarto.getHotel() == null) {
            throw new RegraNegocioException("Hotel inválido");
        }
    }
}
