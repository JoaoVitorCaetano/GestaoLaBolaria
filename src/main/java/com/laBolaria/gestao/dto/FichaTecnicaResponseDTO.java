package com.laBolaria.gestao.dto;

import com.laBolaria.gestao.model.TbProduto;

import java.math.BigDecimal;
import java.util.List;

public record FichaTecnicaResponseDTO(
        Long produtoId,
        String nomeProduto,
        List<ItemFichaTecnicaResponseDTO> itens,
        BigDecimal custoTotal
) {
    public static FichaTecnicaResponseDTO from(TbProduto produto, List<ItemFichaTecnicaResponseDTO> itens) {
        BigDecimal custoTotal = itens.stream()
                .map(ItemFichaTecnicaResponseDTO::custoSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new FichaTecnicaResponseDTO(
                produto.getId(),
                produto.getNome(),
                itens,
                custoTotal
        );
    }
}
