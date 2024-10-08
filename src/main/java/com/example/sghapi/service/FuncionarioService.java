package com.example.sghapi.service;

import com.example.sghapi.exception.RegraNegocioException;
import com.example.sghapi.model.entity.Funcionario;
import com.example.sghapi.model.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FuncionarioService {
    private FuncionarioRepository repository;

    public FuncionarioService(FuncionarioRepository repository){
        this.repository = repository;
    }

    public List<Funcionario> getFuncionarios(){
        return repository.findAll();
    }

    public Optional<Funcionario> getFuncionarioById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Funcionario salvar(Funcionario funcionario){
        validar(funcionario);
        return repository.save(funcionario);
    }
    @Transactional
    public void excluir(Funcionario funcionario){
        Objects.requireNonNull(funcionario.getId());
        repository.delete(funcionario);
    }

    private void validar(Funcionario funcionario){
        if(funcionario.getNome() == null || funcionario.getNome().trim().equals("")){
            throw new RegraNegocioException("Nome inválido");
        }
        if(funcionario.getEmail() == null || funcionario.getEmail().trim().equals("")){
            throw new RegraNegocioException("Email inválido");
        }
        if(funcionario.getSenha() == null || funcionario.getSenha().trim().equals("")){
            throw new RegraNegocioException("Senha inválida");
        }
        if(funcionario.getMatricula() == null || funcionario.getMatricula().trim().equals("")){
            throw new RegraNegocioException("Matrícula inválida");
        }
        if(funcionario.getCargo() == null || funcionario.getCargo().trim().equals("")){
            throw new RegraNegocioException("Cargo inválido");
        }
    }
}
