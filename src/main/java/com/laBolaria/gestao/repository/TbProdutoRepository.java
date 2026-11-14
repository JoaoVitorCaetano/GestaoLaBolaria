package com.laBolaria.gestao.repository;

import com.laBolaria.gestao.model.TbProduto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TbProdutoRepository extends JpaRepository<TbProduto, Long> {
    boolean existsByNomeIgnoreCase(@NotBlank(message = "O nome é obrigatório") @Size(max = 100, message = "O nome deve ter no máximo 120 caracteres") String name);

    Optional<TbProduto> findByNomeIgnoreCase(String nome);
}
