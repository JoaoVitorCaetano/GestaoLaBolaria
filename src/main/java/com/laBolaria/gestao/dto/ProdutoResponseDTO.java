package com.laBolaria.gestao.dto;

import com.laBolaria.gestao.model.CategoriaProduto;
import com.laBolaria.gestao.model.TbIngrediente;
import com.laBolaria.gestao.model.TbProduto;
import com.laBolaria.gestao.model.UnidadeMedida;

import java.math.BigDecimal;

public record ProdutoResponseDTO(
        Long id,
        String name,
        CategoriaProduto categoriaProduto,
        BigDecimal precoVenda,
        Integer redimentoFatias,
        BigDecimal custoTotalReceita
) {
    public static ProdutoResponseDTO from(TbProduto produto) {
        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getCategoriaProduto(),
                produto.getPrecoVenda(),
                produto.getRedimentoFatias(),
                produto.calcularCustoTotal()
        );
    }
}
