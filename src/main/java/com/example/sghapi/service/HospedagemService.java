package com.example.sghapi.service;

import com.example.sghapi.exception.RegraNegocioException;
import com.example.sghapi.model.entity.Funcionario;
import com.example.sghapi.model.entity.Hospedagem;
import com.example.sghapi.model.repository.HospedagemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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

    public List<Hospedagem> getHospedagensByFuncionario(Optional<Funcionario> funcionario){
        return repository.findByFuncionario(funcionario);
    }

    @Transactional
    public Hospedagem salvar(Hospedagem hospedagem){
        validar(hospedagem);
        return repository.save(hospedagem);
    }

    @Transactional
    public void excluir(Hospedagem hospedagem){
        Objects.requireNonNull(hospedagem.getId());
        repository.delete(hospedagem);
    }

    public void validar(Hospedagem hospedagem){
        if(hospedagem.getCheckInReal() == null) {
            throw new RegraNegocioException("Check-in inválido");
        }
        if(hospedagem.getCheckOutReal() == null) {
            throw new RegraNegocioException("Check-out inválido");
        }
        if(hospedagem.getGastos() < 0) {
            throw new RegraNegocioException("Gastos inválidos");
        }
        if (hospedagem.getFuncionario() == null) {
            throw new RegraNegocioException("Funcionário inválido");
        }
        if (hospedagem.getReserva() == null) {
            throw new RegraNegocioException("Reserva inválida");
        }
    }
}
