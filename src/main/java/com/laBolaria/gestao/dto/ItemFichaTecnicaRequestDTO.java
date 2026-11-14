package com.laBolaria.gestao.dto;

import com.laBolaria.gestao.model.UnidadeMedida;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Schema(description = "Dados para adicionar ou atualizar um ingrediente na ficha técnica (receita) de um produto")
public record ItemFichaTecnicaRequestDTO(
        @Schema(
                description = "ID do ingrediente que será utilizado na receita",
                example = "1"
        )
        @NotNull(message = "O ID é obrigatório")
        Long ingredienteId,

        @Schema(
                description = "Quantidade do ingrediente utilizada na receita",
                example = "250.000"
        )
        @NotNull
        @Positive
        @Digits(integer = 7, fraction = 3)
        BigDecimal quantidade

) {
}
