package com.laBolaria.gestao.service;

import com.laBolaria.gestao.dto.ProdutoRequestDTO;
import com.laBolaria.gestao.dto.ProdutoResponseDTO;
import com.laBolaria.gestao.exception.RecursoNaoEncontradoException;
import com.laBolaria.gestao.exception.RegraDeNegocioException;
import com.laBolaria.gestao.model.TbProduto;
import com.laBolaria.gestao.repository.TbProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TbProdutoService {
    private final TbProdutoRepository produtoRepository;

    @Transactional(readOnly = true)
    public Page<ProdutoResponseDTO> listar(Pageable pageable){
        return produtoRepository.findAll(pageable)
                .map(ProdutoResponseDTO::from);
    }

    @Transactional(readOnly = true)
    public ProdutoResponseDTO buscarPorId(Long id){
        return ProdutoResponseDTO.from(obterEntidade(id));
    }

    @Transactional
    public ProdutoResponseDTO criar(ProdutoRequestDTO produto){
        if(produtoRepository.existsByNomeIgnoreCase(produto.name())){
            throw new RegraDeNegocioException("Já existe um produto com esse nome: " + produto.name());
        }

        TbProduto novoProduto = TbProduto.builder()
                .nome(produto.name())
                .categoriaProduto(produto.categoriaProduto())
                .precoVenda(produto.precoVenda())
                .redimentoFatias(produto.redimentoFatias())
                .build();

        TbProduto produtoSalvo = produtoRepository.save(novoProduto);
        return ProdutoResponseDTO.from(produtoSalvo);
    }

    @Transactional
    public ProdutoResponseDTO atualizar(ProdutoRequestDTO dto, Long id){
        TbProduto produto = obterEntidade(id);

        if(!produto.getNome().equalsIgnoreCase(dto.name()) && produtoRepository.existsByNomeIgnoreCase(dto.name())){
            throw new RegraDeNegocioException("Já existe outro produto cadastrado com o nome: " + dto.name());
        }

        produto.setNome(dto.name());
        produto.setCategoriaProduto(dto.categoriaProduto());
        produto.setPrecoVenda(dto.precoVenda());
        produto.setRedimentoFatias(dto.redimentoFatias());

        TbProduto produtoAtualizado = produtoRepository.save(produto);
        return ProdutoResponseDTO.from(produtoAtualizado);
    }

    @Transactional
    public void excluir(Long id){
        TbProduto produto = obterEntidade(id);

        try{
            produtoRepository.delete(produto);
            produtoRepository.flush();
        } catch (DataIntegrityViolationException e){
            throw  new RegraDeNegocioException("Não é possível excluir o  produto: existem registros vinculados a ele.");
        }
    }

    //-------------- APOIO --------------------
    private TbProduto obterEntidade(Long id){
        return produtoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado com o id: " + id));
    }
}
