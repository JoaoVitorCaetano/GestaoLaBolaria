package com.laBolaria.gestao.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@Component
public class AccessKeyFilter extends OncePerRequestFilter {

    public static final String HEADER_NAME = "X-Access-Key";

    @Value("${app.security.access-key:}")
    private String configuredPlainKey;

    @Value("${app.security.access-key-sha256:ac3d3b406457644a5a80ea64b6d76760ab37944635397e894d493b045fb72c4a}")
    private String configuredSha256;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        String path = request.getRequestURI();
        if (!path.startsWith("/api/")) {
            return true;
        }
        return path.startsWith("/api/auth/");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String providedKey = request.getHeader(HEADER_NAME);

        if (isValidKey(providedKey)) {
            filterChain.doFilter(request, response);
            return;
        }

        String origin = request.getHeader("Origin");
        if (origin != null && !origin.isBlank()) {
            response.setHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Vary", "Origin");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, X-Access-Key, Authorization");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        }

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("""
                {"status":401,"erro":"Acesso restrito. Informe a chave de acesso da confeitaria (X-Access-Key) ou utilize o Modo Demonstração no front-end."}
                """.strip());
    }

    public boolean isValidKey(String candidate) {
        if (candidate == null || candidate.isBlank()) {
            return false;
        }
        String trimmed = candidate.trim();

        if (configuredPlainKey != null && !configuredPlainKey.isBlank()) {
            if (MessageDigest.isEqual(
                    trimmed.getBytes(StandardCharsets.UTF_8),
                    configuredPlainKey.trim().getBytes(StandardCharsets.UTF_8)
            )) {
                return true;
            }
        }

        if (configuredSha256 != null && !configuredSha256.isBlank()) {
            String candidateSha256 = sha256Hex(trimmed);
            return MessageDigest.isEqual(
                    candidateSha256.getBytes(StandardCharsets.UTF_8),
                    configuredSha256.trim().toLowerCase().getBytes(StandardCharsets.UTF_8)
            );
        }

        return false;
    }

    private String sha256Hex(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Algoritmo SHA-256 indisponível", e);
        }
    }
}
