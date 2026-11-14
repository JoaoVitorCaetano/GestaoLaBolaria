package com.laBolaria.gestao.repository;

import com.laBolaria.gestao.model.TbIngrediente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TbIngredienteRepository extends JpaRepository<TbIngrediente, Long>{
    boolean existsByNomeIgnoreCase(@NotBlank(message = "O nome é obrigatório") @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres") String name);

    Optional<TbIngrediente> findByNomeIgnoreCase(String nome);
}
