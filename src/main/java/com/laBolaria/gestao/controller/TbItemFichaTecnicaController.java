package com.laBolaria.gestao.controller;

import com.laBolaria.gestao.dto.FichaTecnicaResponseDTO;
import com.laBolaria.gestao.dto.ItemFichaTecnicaRequestDTO;
import com.laBolaria.gestao.dto.ItemFichaTecnicaResponseDTO;
import com.laBolaria.gestao.service.TbItemFichaTecnicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos/{produtoId}/itens")
@RequiredArgsConstructor
@Tag(name = "Ficha Técnica (Item do Produto)", description = "Endpoints para gerenciar os ingredientes da receita de um produto")
public class TbItemFichaTecnicaController {

    private final TbItemFichaTecnicaService itemFichaTecnicaService;

    @Operation(summary = "Listar itens da ficha técnica", description = "Retorna todos os ingredientes e custos que compõem a receita do produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Itens da ficha técnica recuperados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content)
    })
    @GetMapping
    public ResponseEntity<FichaTecnicaResponseDTO> listar(
            @Parameter(description = "ID do produto", example = "1") @PathVariable Long produtoId
    ){
        return ResponseEntity.ok(itemFichaTecnicaService.listarPorProduto(produtoId));
    }

    @Operation(summary = "Adicionar ingrediente à ficha técnica", description = "Vincula um ingrediente e sua quantidade à receita do produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ingrediente adicionado à ficha técnica com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou ingrediente já cadastrado na receita", content = @Content),
            @ApiResponse(responseCode = "404", description = "Produto ou ingrediente não encontrado", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ItemFichaTecnicaResponseDTO> adicionar(
            @Parameter(description = "ID do produto", example = "1") @PathVariable Long produtoId,
            @Valid @RequestBody ItemFichaTecnicaRequestDTO request
    ){
        ItemFichaTecnicaResponseDTO criado = itemFichaTecnicaService.adicionar(produtoId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @Operation(summary = "Atualizar quantidade do item na receita", description = "Altera a quantidade utilizada de um ingrediente na ficha técnica do produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quantidade atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou item não pertence ao produto", content = @Content),
            @ApiResponse(responseCode = "404", description = "Produto ou item não encontrado", content = @Content)
    })
    @PutMapping("/{itemId}")
    public ResponseEntity<ItemFichaTecnicaResponseDTO> atualizarQuantidade(
            @Parameter(description = "ID do produto", example = "1") @PathVariable Long produtoId,
            @Parameter(description = "ID do item da ficha técnica", example = "1") @PathVariable Long itemId,
            @Valid @RequestBody ItemFichaTecnicaRequestDTO request
    ){
        return ResponseEntity.ok(itemFichaTecnicaService.atualizarQuantidade(produtoId, itemId, request));
    }

    @Operation(summary = "Remover ingrediente da ficha técnica", description = "Remove um item da receita do produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item removido da ficha técnica com sucesso"),
            @ApiResponse(responseCode = "400", description = "O item não pertence ao produto informado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Produto ou item não encontrado", content = @Content)
    })
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do produto", example = "1") @PathVariable Long produtoId,
            @Parameter(description = "ID do item da ficha técnica", example = "1") @PathVariable Long itemId
    ){
        itemFichaTecnicaService.excluir(produtoId, itemId);
        return ResponseEntity.noContent().build();
    }

}
