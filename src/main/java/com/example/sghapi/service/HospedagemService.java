package com.example.sghapi.service;

import com.example.sghapi.model.entity.Hospedagem;
import com.example.sghapi.model.repository.HospedagemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HospedagemService {
    private HospedagemRepository repository;

    public HospedagemService(HospedagemRepository repository){
        this.repository = repository;
    }

    public List<Hospedagem> getHospedagens(){
        return repository.findAll();
    }

    public Optional<Hospedagem> getHospedagemById(Long id){
        return repository.findById(id);
    }
}
