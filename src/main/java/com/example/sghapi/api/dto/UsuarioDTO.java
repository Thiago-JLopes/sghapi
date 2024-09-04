package com.example.sghapi.api.dto;

import com.example.sghapi.model.entity.Categoria;
import com.example.sghapi.model.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;


public class UsuarioDTO {
    private Long id;
    private String login;
    private String cpf;
    private String senha;
    private String senhaRepeticao;
    private boolean admin;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Long id, String login, String cpf, String senha, String senhaRepeticao, boolean admin) {
        this.id = id;
        this.login = login;
        this.cpf = cpf;
        this.senha = senha;
        this.senhaRepeticao = senhaRepeticao;
        this.admin = admin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSenhaRepeticao() {
        return senhaRepeticao;
    }

    public void setSenhaRepeticao(String senhaRepeticao) {
        this.senhaRepeticao = senhaRepeticao;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public static UsuarioDTO create(Usuario usuario) {
        ModelMapper modelMapper = new ModelMapper();
        UsuarioDTO dto = modelMapper.map(usuario, UsuarioDTO.class);
        return dto;
    }

}
