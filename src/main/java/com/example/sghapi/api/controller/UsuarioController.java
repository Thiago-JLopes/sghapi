package com.example.sghapi.api.controller;

import com.example.sghapi.api.dto.UsuarioDTO;
import com.example.sghapi.exception.RegraNegocioException;
import com.example.sghapi.model.entity.Usuario;
import com.example.sghapi.service.UsuarioService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/usuarios")
@Api("Api de Usuários")
@CrossOrigin
public class UsuarioController {

    private PasswordEncoder passwordEncoder;
    private final UsuarioService service;


    public UsuarioController(UsuarioService service, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    @ApiOperation("Obter todos os usuarios")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Todos usuarios retornados com sucesso"),
            @ApiResponse(code = 204, message = "Nenhum usuario encontrado")
    })
    public ResponseEntity get() {
        List<Usuario> usuarios = service.getUsuarios();
        return ResponseEntity.ok(usuarios.stream().map(UsuarioDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter usuario")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuario encontrado"),
            @ApiResponse(code = 404, message = "Usuario não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do usuario")Long id) {
        Optional<Usuario> usuario = service.getUsuarioById(id);
        if(!usuario.isPresent()){
            return new ResponseEntity("usuario não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(usuario.map(UsuarioDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salvar dados de um usuario")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Usuario salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar usuario")
    })
    public ResponseEntity post(@RequestBody UsuarioDTO dto) {
        try {
            if(dto.getSenha() == null || dto.getSenha().trim().equals("") || dto.getSenhaRepeticao() == null || dto.getSenhaRepeticao().trim().equals("")){
                return ResponseEntity.badRequest().body("Senha inválida");
            }
            if(!dto.getSenha().equals(dto.getSenhaRepeticao())){
                return ResponseEntity.badRequest().body("Senhas não conferem");
            }
            Usuario usuario = converter(dto);
            String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
            usuario.setSenha(senhaCriptografada);
            usuario = service.salvar(usuario);
            return new ResponseEntity(usuario, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualizar um usuario")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuario atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Usuario não encontrado"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 409, message = "Conflito na requisição")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, UsuarioDTO dto) {
        if (!service.getUsuarioById(id).isPresent()) {
            return new ResponseEntity("Usuario não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            if(dto.getSenha() == null || dto.getSenha().trim().equals("") || dto.getSenhaRepeticao() == null || dto.getSenhaRepeticao().trim().equals("")){
                return ResponseEntity.badRequest().body("Senha inválida");
            }
            if(!dto.getSenha().equals(dto.getSenhaRepeticao())){
                return ResponseEntity.badRequest().body("Senhas não conferem");
            }
            Usuario usuario = converter(dto);
            usuario.setId(id);
            service.salvar(usuario);
            return ResponseEntity.ok(usuario);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir uma usuario")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Usuario excluído com sucesso"),
            @ApiResponse(code = 404, message = "Usuario não encontrado"),
            @ApiResponse(code = 400, message = "Requisição inválida")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Usuario> usuario = service.getUsuarioById(id);
        if (!usuario.isPresent()) {
            return new ResponseEntity("Usuario não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(usuario.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public Usuario converter(UsuarioDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Usuario.class);
    }
}
