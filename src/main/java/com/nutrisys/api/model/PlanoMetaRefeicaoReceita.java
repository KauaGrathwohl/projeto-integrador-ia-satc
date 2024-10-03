package com.nutrisys.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PLANOS_METAS_REFEICOES_RECEITAS")
public class PlanoMetaRefeicaoReceita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_ENTIDADES", nullable = false)
    private Entidade entidade;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIOS", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ID_PLANOS_METAS_REFEICOES", nullable = false)
    private PlanoMetaRefeicao planoMetaRefeicao;

    @ManyToOne
    @JoinColumn(name = "ID_RECEITAS", nullable = false)
    private Receita receita;

    @Column(name = "DESCRICAO", length = 1000, nullable = false)
    private String descricao;

    @Column(name = "QTD_CALORIS_RECEITA", nullable = false)
    private Integer qtdCaloriasReceita;

    @Column(name = "QTD_PROTEINAS_RECEITA", nullable = false)
    private Integer qtdProteinasReceita;

    @Column(name = "QTD_CARBOIDRATOS_RECEITA", nullable = false)
    private Integer qtdCarboidratosReceita;

}