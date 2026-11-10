package com.laBolaria.gestao.dto;

import com.laBolaria.gestao.model.TbIngrediente;
import com.laBolaria.gestao.model.UnidadeMedida;

import java.math.BigDecimal;

public record IngredienteResponseDTO(
        Long id,
        String name,
        UnidadeMedida unidadeMedida,
        BigDecimal custoUnitarioBase,
        BigDecimal estoqueAtual
) {
    public static IngredienteResponseDTO from(TbIngrediente ingrediente) {
        return new IngredienteResponseDTO(
                ingrediente.getId(),
                ingrediente.getNome(),
                ingrediente.getUnidadeMedida(),
                ingrediente.getCustoUnitarioBase(),
                ingrediente.getEstoque_atual()
        );
    }
}