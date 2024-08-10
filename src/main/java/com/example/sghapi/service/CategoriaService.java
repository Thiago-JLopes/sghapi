package com.example.sghapi.service;

import com.example.sghapi.model.entity.Categoria;
import com.example.sghapi.model.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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

    @Transactional
    public Categoria salvar(Categoria categoria) {
        validar(categoria);
        return repository.save(categoria);
    }

    @Transactional
    public void excluir(Categoria categoria) {
        Objects.requireNonNull(categoria.getId());
        repository.delete(categoria);
    }

    public void validar(Categoria categoria) {
        if(categoria.getPreco() < 0) {
            throw new IllegalArgumentException("Preço inválido");
        }
        if(categoria.getDescricao().isEmpty()) {
            throw new IllegalArgumentException("Descrição inválida");
        }
        if(categoria.getQntCamaIndividual() < 0) {
            throw new IllegalArgumentException("Quantidade de camas individuais inválida");
        }
        if(categoria.getQntCamaCasal() < 0) {
            throw new IllegalArgumentException("Quantidade de camas casal inválida");
        }
        if(categoria.getComodidadeCrianca() < 0) {
            throw new IllegalArgumentException("Quantidade de comodidades para crianças inválida");
        }
        if(categoria.getQntTVAnInt() < 0) {
            throw new IllegalArgumentException("Quantidade de TV's com internet inválida");
        }
    }

}
