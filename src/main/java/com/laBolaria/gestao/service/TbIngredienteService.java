package com.laBolaria.gestao.service;

import com.laBolaria.gestao.dto.IngredienteRequestDTO;
import com.laBolaria.gestao.dto.IngredienteResponseDTO;
import com.laBolaria.gestao.exception.RecursoNaoEncontradoException;
import com.laBolaria.gestao.exception.RegraDeNegocioException;
import com.laBolaria.gestao.model.TbIngrediente;
import com.laBolaria.gestao.repository.TbIngredienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TbIngredienteService {
    private final TbIngredienteRepository ingredienteRepository;

    public Page<IngredienteResponseDTO> listar(Pageable pageable){
        return ingredienteRepository.findAll(pageable)
                .map(IngredienteResponseDTO::from);
    }

    public IngredienteResponseDTO buscarPorId(Long id) {
        return IngredienteResponseDTO.from(obterEntidade(id));
    }

    @Transactional
    public IngredienteResponseDTO criar(IngredienteRequestDTO ingrediente){
        if (ingredienteRepository.existsByNomeIgnoreCase(ingrediente.name())) {
            throw new RegraDeNegocioException("Já existe um ingrediente com esse nome: " + ingrediente.name());
        }

        TbIngrediente novoIngrediente = TbIngrediente.builder()
                .nome(ingrediente.name())
                .unidadeMedida(ingrediente.unidadeMedida())
                .custoUnitarioBase(ingrediente.custoUnitarioBase())
                .estoque_atual(ingrediente.estoqueAtual())
                .build();

        TbIngrediente ingredienteSalvo = ingredienteRepository.save(novoIngrediente);
        return IngredienteResponseDTO.from(ingredienteSalvo);
    }

    @Transactional
    public IngredienteResponseDTO atualizar(IngredienteRequestDTO dto, Long id){
        TbIngrediente ingrediente = obterEntidade(id);

        if (!ingrediente.getNome().equalsIgnoreCase(dto.name()) && ingredienteRepository.existsByNomeIgnoreCase(dto.name())){
            throw new RegraDeNegocioException("Já existe outro ingrediente cadastrado com o nome: " + dto.name());
        }

        ingrediente.setNome(dto.name());
        ingrediente.setUnidadeMedida(dto.unidadeMedida());
        ingrediente.setCustoUnitarioBase(dto.custoUnitarioBase());
        ingrediente.setEstoque_atual(dto.estoqueAtual());

        TbIngrediente ingredienteAtualizado = ingredienteRepository.save(ingrediente);
        return IngredienteResponseDTO.from(ingredienteAtualizado);
    }

    @Transactional
    public void excluir(Long id){
        TbIngrediente ingrediente = obterEntidade(id);

        try{
            ingredienteRepository.delete(ingrediente);
            ingredienteRepository.flush();
        } catch (DataIntegrityViolationException e){
            throw new RegraDeNegocioException("Não é possível excluir o ingrediente: existem registros vinculados a ele.");
        }
    }

    //-------------- APOIO --------------------
    private TbIngrediente obterEntidade(Long id){
        return ingredienteRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Ingrediente não encontrado com o id: " + id));
    }
}
