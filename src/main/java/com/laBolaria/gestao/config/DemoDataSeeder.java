package com.laBolaria.gestao.config;

import com.laBolaria.gestao.model.*;
import com.laBolaria.gestao.repository.TbIngredienteRepository;
import com.laBolaria.gestao.repository.TbItemFichaTecnicaRepository;
import com.laBolaria.gestao.repository.TbProdutoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Seeder público de demonstração (Portfólio / GitHub).
 * Popula o banco APENAS se estiver completamente vazio (ex: quando alguém clona o repositório
 * do GitHub sem o arquivo confidencial ReceituarioDataSeeder).
 * No banco de produção da confeitaria, como já existem dados, este seeder é ignorado automaticamente.
 */
@Component
@Order(2)
@RequiredArgsConstructor
@Slf4j
public class DemoDataSeeder implements CommandLineRunner {

    private final TbIngredienteRepository ingredienteRepository;
    private final TbProdutoRepository produtoRepository;
    private final TbItemFichaTecnicaRepository itemFichaTecnicaRepository;

    private final Map<String, TbIngrediente> cacheIngredientes = new LinkedHashMap<>();

    @Override
    @Transactional
    public void run(String... args) {
        if (produtoRepository.count() > 0 || ingredienteRepository.count() > 0) {
            log.info("Banco de dados já possui registros. Ignorando carga de demonstração (DemoDataSeeder).");
            return;
        }

        log.info("Banco vazio detectado — populando dados fictícios de demonstração para Portfólio...");
        seedIngredientesFicticios();
        seedProdutosFicticios();
        log.info("Carga de demonstração (Portfólio) concluída com sucesso!");
    }

    private void seedIngredientesFicticios() {
        ing("Ovo Caipira (Demo)", UnidadeMedida.UNIDADE, "0.9000", "240.000");
        ing("Farinha de Trigo (Demo)", UnidadeMedida.GRAMA, "0.0060", "25000.000");
        ing("Açúcar Refinado (Demo)", UnidadeMedida.GRAMA, "0.0050", "20000.000");
        ing("Leite Integral (Demo)", UnidadeMedida.MILILITRO, "0.0060", "15000.000");
        ing("Manteiga sem Sal (Demo)", UnidadeMedida.GRAMA, "0.0420", "5000.000");
        ing("Chocolate em Pó 50% (Demo)", UnidadeMedida.GRAMA, "0.0490", "8000.000");
        ing("Leite Condensado (Demo)", UnidadeMedida.GRAMA, "0.0190", "12000.000");
        ing("Creme de Leite (Demo)", UnidadeMedida.GRAMA, "0.0160", "10000.000");
        ing("Cenoura Fresca (Demo)", UnidadeMedida.GRAMA, "0.0070", "6000.000");
        ing("Fermento Químico (Demo)", UnidadeMedida.GRAMA, "0.0350", "2000.000");
        ing("Creme de Avelã (Demo)", UnidadeMedida.GRAMA, "0.0680", "4000.000");
        ing("Morango Selecionado (Demo)", UnidadeMedida.UNIDADE, "0.6000", "150.000");
    }

    private void seedProdutosFicticios() {
        produto("Bolo Clássico da Vovó (Demo)", CategoriaProduto.CASEIRO_TRADICIONAL, "32.00", 12, Map.of(
                "Ovo Caipira (Demo)", "3",
                "Farinha de Trigo (Demo)", "300",
                "Açúcar Refinado (Demo)", "220",
                "Leite Integral (Demo)", "200",
                "Manteiga sem Sal (Demo)", "100",
                "Fermento Químico (Demo)", "15"
        ));

        produto("Bolo de Cenoura Tradicional (Demo)", CategoriaProduto.CASEIRO_TRADICIONAL, "34.00", 12, Map.of(
                "Ovo Caipira (Demo)", "3",
                "Cenoura Fresca (Demo)", "250",
                "Farinha de Trigo (Demo)", "280",
                "Açúcar Refinado (Demo)", "200",
                "Manteiga sem Sal (Demo)", "90",
                "Fermento Químico (Demo)", "15"
        ));

        produto("Vulcão de Brigadeiro Gourmet (Demo)", CategoriaProduto.VULCAO, "56.00", 10, Map.of(
                "Ovo Caipira (Demo)", "3",
                "Farinha de Trigo (Demo)", "260",
                "Açúcar Refinado (Demo)", "180",
                "Chocolate em Pó 50% (Demo)", "80",
                "Leite Condensado (Demo)", "395",
                "Creme de Leite (Demo)", "200",
                "Fermento Químico (Demo)", "15"
        ));

        produto("Piscininha Cenoura c/ Avelã (Demo)", CategoriaProduto.VULCAO, "48.00", 8, Map.of(
                "Ovo Caipira (Demo)", "2",
                "Cenoura Fresca (Demo)", "180",
                "Farinha de Trigo (Demo)", "200",
                "Açúcar Refinado (Demo)", "150",
                "Creme de Avelã (Demo)", "140",
                "Creme de Leite (Demo)", "150",
                "Fermento Químico (Demo)", "10"
        ));

        produto("Torta Festa Morango & Chocolate (Demo)", CategoriaProduto.RECHEADO_FESTA, "88.00", 10, Map.of(
                "Ovo Caipira (Demo)", "4",
                "Farinha de Trigo (Demo)", "300",
                "Açúcar Refinado (Demo)", "200",
                "Chocolate em Pó 50% (Demo)", "90",
                "Leite Condensado (Demo)", "395",
                "Creme de Leite (Demo)", "300",
                "Morango Selecionado (Demo)", "12",
                "Fermento Químico (Demo)", "15"
        ));

        produto("Torta Suprema de Avelã (Demo)", CategoriaProduto.RECHEADO_FESTA, "96.00", 10, Map.of(
                "Ovo Caipira (Demo)", "4",
                "Farinha de Trigo (Demo)", "280",
                "Chocolate em Pó 50% (Demo)", "100",
                "Creme de Avelã (Demo)", "220",
                "Leite Condensado (Demo)", "395",
                "Creme de Leite (Demo)", "300",
                "Fermento Químico (Demo)", "15"
        ));

        produto("Bolo no Pote Sensação (Demo)", CategoriaProduto.POTE, "15.00", 1, Map.of(
                "Farinha de Trigo (Demo)", "60",
                "Chocolate em Pó 50% (Demo)", "20",
                "Leite Condensado (Demo)", "80",
                "Creme de Leite (Demo)", "50",
                "Morango Selecionado (Demo)", "3"
        ));

        produto("Bolo no Pote Duplo Chocolate (Demo)", CategoriaProduto.POTE, "16.00", 1, Map.of(
                "Farinha de Trigo (Demo)", "60",
                "Chocolate em Pó 50% (Demo)", "25",
                "Leite Condensado (Demo)", "70",
                "Creme de Avelã (Demo)", "35",
                "Creme de Leite (Demo)", "40"
        ));
    }

    private void ing(String nome, UnidadeMedida unidade, String custoUnitario, String estoqueInicial) {
        TbIngrediente novo = TbIngrediente.builder()
                .nome(nome)
                .unidadeMedida(unidade)
                .custoUnitarioBase(new BigDecimal(custoUnitario))
                .estoque_atual(new BigDecimal(estoqueInicial))
                .build();
        cacheIngredientes.put(nome, ingredienteRepository.save(novo));
    }

    private void produto(String nome, CategoriaProduto categoria, String precoVenda, Integer rendimentoFatias, Map<String, String> itens) {
        TbProduto salvo = produtoRepository.save(TbProduto.builder()
                .nome(nome)
                .categoriaProduto(categoria)
                .precoVenda(new BigDecimal(precoVenda))
                .redimentoFatias(rendimentoFatias)
                .build());

        for (Map.Entry<String, String> entry : itens.entrySet()) {
            TbIngrediente ing = cacheIngredientes.get(entry.getKey());
            if (ing != null) {
                itemFichaTecnicaRepository.save(TbItemFichaTecnica.builder()
                        .produto(salvo)
                        .ingrediente(ing)
                        .quantidade(new BigDecimal(entry.getValue()))
                        .build());
            }
        }
    }
}
