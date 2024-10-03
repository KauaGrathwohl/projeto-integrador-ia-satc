package com.nutrisys.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "PLANOS_METAS_REFEICOES")
public class PlanoMetaRefeicao {

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
    @JoinColumn(name = "ID_PLANOS_METAS", nullable = false)
    private PlanoMeta planoMeta;

    @Column(name = "QTD_CALORIAS_REFEICAO", nullable = false)
    private Integer qtdCaloriasRefeicao;

    @Column(name = "QTD_PROTEINAS_REFEICAO", nullable = false)
    private Integer qtdProteinasRefeicao;

    @Column(name = "QTD_CARBOIDRATOS_REFEICAO", nullable = false)
    private Integer qtdCarboidratosRefeicao;

    @Column(name = "TIPO_REFEICAO", length = 1, nullable = false)
    private String tipoRefeicao;

}