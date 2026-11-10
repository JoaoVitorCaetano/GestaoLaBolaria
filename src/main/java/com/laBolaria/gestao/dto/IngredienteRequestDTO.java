package com.laBolaria.gestao.dto;

import com.laBolaria.gestao.model.UnidadeMedida;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "Dados para cadastro ou atualização de um ingrediente")
public record IngredienteRequestDTO(
        @Schema(
                description = "Nome do ingrediente",
                example = "Farinha de Trigo"
        )
        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        String name,

        @Schema(
                description = "Unidade de medida base do ingrediente. Opções aceitas: GRAMA, MILILITRO, UNIDADE",
                example = "GRAMA",
                allowableValues = {"GRAMA", "MILILITRO", "UNIDADE"}
        )
        @NotNull(message = "A unidade de medida é obrigatória. Opções válidas: GRAMA, MILILITRO, UNIDADE")
        UnidadeMedida unidadeMedida,

        @Schema(
                description = "Custo unitário base por GRAMA, MILILITRO ou UNIDADE (padrão: número positivo ou zero, até 8 dígitos inteiros e até 4 casas decimais, usando ponto como separador)",
                example = "0.0085"
        )
        @NotNull(message = "O custo unitário base é obrigatório (ex: 0.0085)")
        @PositiveOrZero(message = "O custo unitário base deve ser maior ou igual a zero (ex: 0.0085)")
        @Digits(integer = 8, fraction = 4, message = "O custo unitário base deve ter no máximo 8 dígitos inteiros e 4 casas decimais (ex: 0.0085)")
        BigDecimal custoUnitarioBase,

        @Schema(
                description = "Quantidade atual em estoque na unidade de medida escolhida (padrão: número positivo ou zero, até 7 dígitos inteiros e até 3 casas decimais, usando ponto como separador)",
                example = "5000.000"
        )
        @NotNull(message = "O estoque atual é obrigatório (ex: 5000.000)")
        @PositiveOrZero(message = "O estoque atual deve ser maior ou igual a zero (ex: 5000.000)")
        @Digits(integer = 7, fraction = 3, message = "O estoque atual deve ter no máximo 7 dígitos inteiros e 3 casas decimais (ex: 5000.000)")
        BigDecimal estoqueAtual
) {
}
