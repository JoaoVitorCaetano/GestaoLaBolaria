package com.laBolaria.gestao.controller;

import com.laBolaria.gestao.config.AccessKeyFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Validação de chave de acesso para o ambiente de produção da confeitaria")
public class AuthController {

    private final AccessKeyFilter accessKeyFilter;

    public record LoginRequestDTO(String senha) {}

    @Operation(
            summary = "Validar senha da confeitaria",
            description = "Verifica se a chave informada corresponde à senha do ambiente real de produção"
    )
    @PostMapping("/validar")
    public ResponseEntity<Map<String, Object>> validar(
            @RequestBody(required = false) LoginRequestDTO body,
            @RequestHeader(value = AccessKeyFilter.HEADER_NAME, required = false) String headerKey
    ) {
        String candidate = (body != null && body.senha() != null && !body.senha().isBlank())
                ? body.senha()
                : headerKey;

        if (accessKeyFilter.isValidKey(candidate)) {
            return ResponseEntity.ok(Map.of(
                    "autenticado", true,
                    "modo", "REAL",
                    "mensagem", "Acesso autorizado ao ambiente de produção La Bolaria."
            ));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                "autenticado", false,
                "mensagem", "Senha incorreta. Para explorar o portfólio sem senha, clique em 'Entrar no Modo Demonstração'."
        ));
    }
}
