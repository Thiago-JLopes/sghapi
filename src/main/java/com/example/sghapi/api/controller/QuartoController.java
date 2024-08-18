package com.example.sghapi.api.controller;

import com.example.sghapi.api.dto.QuartoDTO;
import com.example.sghapi.api.dto.ReservaDTO;
import com.example.sghapi.exception.RegraNegocioException;
import com.example.sghapi.model.entity.Categoria;
import com.example.sghapi.model.entity.Hotel;
import com.example.sghapi.model.entity.Quarto;
import com.example.sghapi.model.entity.Reserva;
import com.example.sghapi.service.CategoriaService;
import com.example.sghapi.service.HotelService;
import com.example.sghapi.service.QuartoService;
import com.example.sghapi.service.ReservaService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/quartos")
@RequiredArgsConstructor
@Api("Api de Quartos")
@CrossOrigin
public class QuartoController {
    private final QuartoService service;
    private final HotelService hotelService;
    private final CategoriaService categoriaService;
    private final ReservaService reservaService;

    @GetMapping()
    @ApiOperation("Obter todos os quartos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Todos quartos retornados com sucesso"),
            @ApiResponse(code = 204, message = "Nenhum quarto encontrado")
    })
    public ResponseEntity get() {
        List<Quarto> quartos = service.getQuartos();
        return ResponseEntity.ok(quartos.stream().map(QuartoDTO::create).collect(Collectors.toList()));
    }
    @ApiOperation("Obter dados de um Quarto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Quarto encontrado"),
            @ApiResponse(code = 404, message = "Quarto não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do quarto") Long id) {
        Optional<Quarto> quarto = service.getQuartoById(id);
        if(!quarto.isPresent()){
            return new ResponseEntity("quarto não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(quarto.map(QuartoDTO::create));
    }

    @GetMapping("{id}/reservas")
    @ApiOperation("Obter todas as reservas de um quarto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Reservas retornadas com sucesso"),
            @ApiResponse(code = 404, message = "Quarto não encontrado")
    })
    public ResponseEntity getQuartos(@PathVariable("id") Long id) {
        Optional<Quarto> quarto = service.getQuartoById(id);
        if(!quarto.isPresent()){
            return new ResponseEntity("Quarto não encontrado", HttpStatus.NOT_FOUND);
        }
        List<Reserva> reservas = reservaService.getReservasByQuarto(quarto);
        return ResponseEntity.ok(reservas.stream().map(ReservaDTO::create).collect(Collectors.toList()));
    }

    @ApiOperation("Salva dados de um Quarto")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Quarto salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar quarto")
    })
    @PostMapping()
    public ResponseEntity post(@RequestBody QuartoDTO dto) {
        try {
            Quarto quarto = converter(dto);
            quarto = service.salvar(quarto);
            return new ResponseEntity(quarto, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza dados de um Quarto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Quarto atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Quarto não encontrado"),
            @ApiResponse(code = 400, message = "Erro ao atualizar quarto")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody QuartoDTO dto) {
        if(!service.getQuartoById(id).isPresent()){
            return new ResponseEntity("quarto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Quarto quarto = converter(dto);
            quarto.setId(id);
            service.salvar(quarto);
            return ResponseEntity.ok(quarto);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui um Quarto")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Quarto excluído com sucesso"),
            @ApiResponse(code = 404, message = "Quarto não encontrado"),
            @ApiResponse(code = 400, message = "Erro ao excluir quarto")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Quarto> quarto = service.getQuartoById(id);
        if(!quarto.isPresent()){
            return new ResponseEntity("quarto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(quarto.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Quarto  converter(QuartoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Quarto quarto = modelMapper.map(dto, Quarto.class);
        if(dto.getIdHotel()!= null) {
            Optional<Hotel> hotel = hotelService.getHotelById(dto.getIdHotel());
            if (!hotel.isPresent()) {
                quarto.setHotel(null);
            } else {
                quarto.setHotel(hotel.get());
            }
        }
        if(dto.getIdCategoria() != null) {
            Optional<Categoria> categoria = categoriaService.getCategoriaById(dto.getIdCategoria());
            if (!categoria.isPresent()) {
                quarto.setCategoria(null);
            } else {
                quarto.setCategoria(categoria.get());
            }
        }
        return quarto;
    }
}
