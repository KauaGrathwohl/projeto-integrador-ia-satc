package com.nutrisys.api.model;

import com.nutrisys.api.enums.TipoRefeicao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@SequenceGenerator(name = "SEQ_PLANOS_METAS_REFEICOES", sequenceName = "SEQ_PLANOS_METAS_REFEICOES", allocationSize = 1)
@Table(name = "PLANOS_METAS_REFEICOES")
public class PlanoMetaRefeicao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PLANOS_METAS_REFEICOES")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_ENTIDADES")
    private Entidade entidade;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIOS")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ID_PLANOS_METAS")
    private PlanoMeta planoMeta;

    @Column(name = "QTD_CALORIAS_REFEICAO")
    private BigDecimal qtdCaloriasRefeicao;

    @Column(name = "QTD_PROTEINAS_REFEICAO")
    private BigDecimal qtdProteinasRefeicao;

    @Column(name = "QTD_CARBOIDRATOS_REFEICAO")
    private BigDecimal qtdCarboidratosRefeicao;

    @Column(name = "QTD_GORDURA_REFEICAO")
    private BigDecimal qtdGorduraRefeicao;

    @Column(name = "TIPO_REFEICAO")
    private TipoRefeicao tipoRefeicao;
}