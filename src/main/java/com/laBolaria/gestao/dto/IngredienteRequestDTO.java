package com.laBolaria.gestao.dto;

import com.laBolaria.gestao.model.UnidadeMedida;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record IngredienteRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        String name,

        @NotBlank(message = "A unidade de medida é obrigatória")
        @Size(max = 20, message = "A unidade de medida deve ter no máximo 20 caracteres")
        UnidadeMedida unidadeMedida,

        @NotBlank(message = "O custo unitário base é obrigatório")
        @PositiveOrZero
        BigDecimal custoUnitarioBase,

        @NotBlank(message = "O estoque atual é obrigatório")
        @PositiveOrZero
        BigDecimal estoqueAtual
) {
}
