package com.laBolaria.gestao.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "produto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TbProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "nome", nullable = false, length = 120)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false, length = 30)
    private CategoriaProduto categoriaProduto;

    @Column(name = "preco_venda", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoVenda;

    @Column(name = "rendimento_fatias", nullable = false, length = 4)
    private Integer redimentoFatias;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<TbItemFichaTecnica> itensFichaTecnica = new ArrayList<>();

    public BigDecimal calcularCustoTotal(){
        if (itensFichaTecnica == null || itensFichaTecnica.isEmpty()){
            return  BigDecimal.ZERO;
        }
        return itensFichaTecnica.stream()
                .map(item -> item.getQuantidade().multiply(item.getIngrediente().getCustoUnitarioBase()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}