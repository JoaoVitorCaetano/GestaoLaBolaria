package com.laBolaria.gestao.repository;

import com.laBolaria.gestao.model.TbItemFichaTecnica;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TbItemFichaTecnicaRepository extends JpaRepository<TbItemFichaTecnica, Long> {

    @EntityGraph(attributePaths = "ingrediente")
    List<TbItemFichaTecnica> findByProdutoId(Long produtoId);

    @Override
    @EntityGraph(attributePaths = {"ingrediente", "produto"})
    Optional<TbItemFichaTecnica> findById(Long id);

    boolean existsByProdutoIdAndIngredienteId(Long produtoId, Long ingredienteId);
}
