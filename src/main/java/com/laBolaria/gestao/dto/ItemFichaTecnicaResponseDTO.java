package com.laBolaria.gestao.dto;

import com.laBolaria.gestao.model.TbItemFichaTecnica;
import com.laBolaria.gestao.model.UnidadeMedida;

import java.math.BigDecimal;

public record ItemFichaTecnicaResponseDTO(
        Long id,
        Long ingredienteId,
        String nomeIngrediente,
        UnidadeMedida unidadeMedida,
        BigDecimal quantidade,
        BigDecimal custoSubtotal
){
    public static ItemFichaTecnicaResponseDTO from(TbItemFichaTecnica item){
        BigDecimal custoSubtotal = item.getQuantidade()
                .multiply(item.getIngrediente().getCustoUnitarioBase());

        return new ItemFichaTecnicaResponseDTO(
                item.getId(),
                item.getIngrediente().getId(),
                item.getIngrediente().getNome(),
                item.getIngrediente().getUnidadeMedida(),
                item.getQuantidade(),
                custoSubtotal
        );
    }
}
