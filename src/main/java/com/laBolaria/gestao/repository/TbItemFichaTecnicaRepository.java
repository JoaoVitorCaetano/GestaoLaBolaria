package com.laBolaria.gestao.repository;

import com.laBolaria.gestao.model.TbItemFichaTecnica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TbItemFichaTecnicaRepository extends JpaRepository<TbItemFichaTecnica, Long> {
    List<TbItemFichaTecnica> findByProdutoId(Long produtoId);

    boolean existsByProdutoIdAndIngredienteId(Long produtoId, Long ingredienteId);
}
