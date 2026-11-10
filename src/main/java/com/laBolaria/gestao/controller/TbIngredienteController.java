package com.laBolaria.gestao.controller;

import com.laBolaria.gestao.dto.IngredienteRequestDTO;
import com.laBolaria.gestao.dto.IngredienteResponseDTO;
import com.laBolaria.gestao.service.TbIngredienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ingredientes")
@RequiredArgsConstructor
@Tag(name = "Ingredientes", description = "Endpoints para gerenciamento de ingredientes")
public class TbIngredienteController {
    private final TbIngredienteService ingredienteService;

    @Operation(summary = "Listar ingredientes paginados", description = "Retorna uma lista paginada de todos os" +
            "ingredientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista paginada de ingredientes recuperada com sucesso")
    })
    @GetMapping
    public ResponseEntity<Page<IngredienteResponseDTO>> listar(Pageable pageable){
        return ResponseEntity.ok(ingredienteService.listar(pageable));
    }

    @Operation(summary = "Buscar ingrediente por ID", description = "Retorna os dados de um ingrediente buscado " +
            "por seu identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingrediente encontrado com sucesso")
    })
    @GetMapping("/{id]")
    public ResponseEntity<IngredienteResponseDTO> buscarPorId(@Parameter(description = "Identificador único " +
            "para ingrediente", example = "1") @PathVariable Long id){
        return ResponseEntity.ok(ingredienteService.buscarPorId(id));
    }

    @Operation(summary = "Criar um novo ingrediente", description = "Cadastra um novo ingrediente no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ingrediente cadastrado com sucesso")
    })
    @PostMapping
    public ResponseEntity<IngredienteResponseDTO> criar(@Valid @RequestBody IngredienteRequestDTO request){
        IngredienteResponseDTO criada = ingredienteService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @Operation(summary = "Atualizar Ingrediente", description = "Atualiza os dados de um ingrediente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingrediente atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos", content = @Content),
            @ApiResponse(responseCode = "401", description = "Ingrediente não encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<IngredienteResponseDTO> atualizar(
            @Parameter(description = "Atualiza os dados de um ingrediente", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody IngredienteRequestDTO request)
    {
        return ResponseEntity.ok(ingredienteService.atualizar(request, id));
    }

    @Operation(summary = "Deletar Ingrediente", description = "Deleta um ingrediente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ingrediente deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Exclusão não permitidas, dados vinculados existentes"),
            @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "Deleta um ingrediente")
            @PathVariable Long id){
        ingredienteService.excluir(id);
        return ResponseEntity.noContent().build();
    }


}
