package com.laBolaria.gestao.controller;

import com.laBolaria.gestao.dto.ProdutoRequestDTO;
import com.laBolaria.gestao.dto.ProdutoResponseDTO;
import com.laBolaria.gestao.service.TbProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
@Tag(name = "Produtos", description = "Endpoints para gerenciamento de ingredientes")
public class TbProdutoController {
    private final TbProdutoService produtoService;

    @Operation(summary = "Listar produtos paginados", description = "Retorna uma lista paginada de todos os" +
            "produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista paginada de ingredientes recuperada com sucesso")
    })
    @GetMapping
    public ResponseEntity<Page<ProdutoResponseDTO>> listar(
            @ParameterObject @PageableDefault(size = 10, sort = "nome")Pageable pageable
            ) {
        return ResponseEntity.ok(produtoService.listar(pageable));
    }

    @Operation(summary = "Buscar produtos por ID", description = "Retorna os dados de um produto buscado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingrediente encontrado com sucesso")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@Parameter(description = "Identificador único para produto", example = "1")
                                                          @PathVariable Long id){
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }

    @Operation(summary = "Criar um novo produto", description = "Cadastra um novo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto cadastrado com sucesso")
    })
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criar(@Valid @RequestBody ProdutoRequestDTO request){
        ProdutoResponseDTO criada = produtoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @Operation(summary = "Atualizar um Produto", description = "Atualiza os dados de um produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos", content = @Content),
            @ApiResponse(responseCode = "401", description = "Produto não encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(
            @Parameter(description = "Atualiza os dados de um produto", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody ProdutoRequestDTO request)
    {
        return ResponseEntity.ok(produtoService.atualizar(request, id));
    }

    @Operation(summary = "Deletar Produto", description = "Deleta um produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Exclusão não permitidas, dados vinculados existentes"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "Deleta um produto")
            @PathVariable Long id){
     produtoService.excluir(id);
     return ResponseEntity.noContent().build();
    }
}
