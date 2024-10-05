package com.nutrisys.api.model;

import com.nutrisys.api.enums.TipoRefeicao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
    private BigDecimal qtdCaloriasReceita;

    @Column(name = "QTD_PROTEINAS_RECEITA")
    private BigDecimal qtdProteinasReceita;

    @Column(name = "QTD_CARBOIDRATOS_RECEITA")
    private BigDecimal qtdCarboidratosReceita;

    @Column(name = "QTD_GORDURA_RECEITA")
    private BigDecimal qtdGorduraReceita;

    @Column(name = "QTD_GRAMAS_RECEITA")
    private BigDecimal qtdGramasReceita;

    @Column(name = "TIPO_REFEICAO")
    private TipoRefeicao tipoRefeicao;
}