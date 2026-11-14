package com.laBolaria.gestao.service;

import com.laBolaria.gestao.dto.FichaTecnicaResponseDTO;
import com.laBolaria.gestao.dto.ItemFichaTecnicaRequestDTO;
import com.laBolaria.gestao.dto.ItemFichaTecnicaResponseDTO;
import com.laBolaria.gestao.exception.RecursoNaoEncontradoException;
import com.laBolaria.gestao.exception.RegraDeNegocioException;
import com.laBolaria.gestao.model.TbIngrediente;
import com.laBolaria.gestao.model.TbItemFichaTecnica;
import com.laBolaria.gestao.model.TbProduto;
import com.laBolaria.gestao.repository.TbIngredienteRepository;
import com.laBolaria.gestao.repository.TbItemFichaTecnicaRepository;
import com.laBolaria.gestao.repository.TbProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TbItemFichaTecnicaService {
    private final TbItemFichaTecnicaRepository itemFichaTecnicaRepository;
    private final TbProdutoRepository produtoRepository;
    private final TbIngredienteRepository ingredienteRepository;

    @Transactional(readOnly = true)
    public FichaTecnicaResponseDTO listarPorProduto(Long produtoId){
        TbProduto produto = obterProduto(produtoId);

        List<ItemFichaTecnicaResponseDTO> itens = itemFichaTecnicaRepository.findByProdutoId(produtoId)
                .stream()
                .map(ItemFichaTecnicaResponseDTO::from)
                .toList();

        return FichaTecnicaResponseDTO.from(produto, itens);
    }

    @Transactional
    public ItemFichaTecnicaResponseDTO adicionar(Long produtoId, ItemFichaTecnicaRequestDTO dto){
        TbProduto produto = obterProduto(produtoId);
        TbIngrediente ingrediente = obterIngrediente(dto.ingredienteId());

        if (itemFichaTecnicaRepository.existsByProdutoIdAndIngredienteId(produtoId, dto.ingredienteId())){
            throw new RegraDeNegocioException("Este ingrediente já está cadastrado na ficha técnica deste produto.");
        }

        TbItemFichaTecnica novoItem = TbItemFichaTecnica.builder()
                .produto(produto)
                .ingrediente(ingrediente)
                .quantidade(dto.quantidade())
                .build();

        TbItemFichaTecnica itemSalvo = itemFichaTecnicaRepository.save(novoItem);
        return ItemFichaTecnicaResponseDTO.from(itemSalvo);
    }

    @Transactional
    public ItemFichaTecnicaResponseDTO atualizarQuantidade(Long produtoId, Long itemId, ItemFichaTecnicaRequestDTO dto){
        TbItemFichaTecnica item = obterItemDoProduto(produtoId, itemId);

        item.setQuantidade(dto.quantidade());

        TbItemFichaTecnica itemAtualizado = itemFichaTecnicaRepository.save(item);
        return ItemFichaTecnicaResponseDTO.from(itemAtualizado);
    }

    @Transactional
    public void excluir(Long produtoId, Long itemId){
        TbItemFichaTecnica item = obterItemDoProduto(produtoId, itemId);
        itemFichaTecnicaRepository.delete(item);
    }

    //-------------- APOIO --------------------
    private TbProduto obterProduto(Long produtoId) {
        return produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado com o id: " + produtoId));
    }

    private TbIngrediente obterIngrediente(Long ingredienteId) {
        return ingredienteRepository.findById(ingredienteId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Ingrediente não encontrado com o id: " + ingredienteId));
    }

    private TbItemFichaTecnica obterItemDoProduto(Long produtoId, Long itemId) {
        obterProduto(produtoId);

        TbItemFichaTecnica item = itemFichaTecnicaRepository.findById(itemId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Item da ficha técnica não encontrado com o id: " + itemId));

        if (!item.getProduto().getId().equals(produtoId)) {
            throw new RegraDeNegocioException("O item informado não pertence à ficha técnica deste produto.");
        }

        return item;
    }
}
