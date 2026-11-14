package com.laBolaria.gestao.dto;

import com.laBolaria.gestao.model.CategoriaProduto;
import com.laBolaria.gestao.model.UnidadeMedida;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Schema(description = "Dados para cadastro ou atualização de um produto")
public record ProdutoRequestDTO(
        @Schema(
                description = "Nome do Produto",
                example = "Bolo de Cenoura Vulcão"
        )
        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 120, message = "O nome deve ter no máximo 120 caracteres")
        String name,

        @Schema(
                description = "Categoria do Produto. Opções aceitas: CASEIRO_TRADICIONAL, VULCAO, RECHEADO_FESTA, POTE",
                example = "GRAMA",
                allowableValues = {"CASEIRO_TRADICIONAL, VULCAO, RECHEADO_FESTA, POTE"}
        )
        @NotNull(message = "A categoria do produto é obrigatória. Opções válidas: CASEIRO_TRADICIONAL, VULCAO, RECHEADO_FESTA, POTE")
        CategoriaProduto categoriaProduto,

        @Schema(
                description = "Preço de Venda do Produto",
                example = "29"
        )
        @NotNull(message = "O preço de venda é obrigatório (ex: 29)")
        @PositiveOrZero(message = "O preço de venda base deve ser maior ou igual a zero (ex: 29)")
        @Digits(integer = 4, fraction = 2, message = "O custo unitário base deve ter no máximo 4 dígitos inteiros e 2 casas decimais (ex: 29,90)")
        BigDecimal precoVenda,

        @Schema(
                description = "Quantidade de fatias que o produto rende",
                example = "12"
        )
        @NotNull(message = "A quantidade de fatias rendidas é obrigatório")
        @PositiveOrZero(message = "A quantidade de fatias rendidas deve ser maior ou igual a zero (ex: 12)")
        @Digits(integer = 2, fraction = 0, message = "O estoque atual deve ter no máximo 3 dígitos inteiros e 0 casas decimais (ex: 12)")
        Integer redimentoFatias
) {}
