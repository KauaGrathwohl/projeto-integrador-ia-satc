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
    @JoinColumn(name = "ID_ENTIDADES")
    private Entidade entidade;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIOS")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ID_PLANOS_METAS_REFEICOES")
    private PlanoMetaRefeicao planoMetaRefeicao;

    @ManyToOne
    @JoinColumn(name = "ID_RECEITAS")
    private Receita receita;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "QTD_CALORIS_RECEITA")
    private Integer qtdCaloriasReceita;

    @Column(name = "QTD_PROTEINAS_RECEITA")
    private Integer qtdProteinasReceita;

    @Column(name = "QTD_CARBOIDRATOS_RECEITA")
    private Integer qtdCarboidratosReceita;
}