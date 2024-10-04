package com.nutrisys.api.model;

import com.nutrisys.api.enums.TipoRefeicao;
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
    @JoinColumn(name = "ID_ENTIDADES")
    private Entidade entidade;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIOS")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ID_PLANOS_METAS")
    private PlanoMeta planoMeta;

    @Column(name = "QTD_CALORIAS_REFEICAO")
    private Integer qtdCaloriasRefeicao;

    @Column(name = "QTD_PROTEINAS_REFEICAO")
    private Integer qtdProteinasRefeicao;

    @Column(name = "QTD_CARBOIDRATOS_REFEICAO")
    private Integer qtdCarboidratosRefeicao;

    @Column(name = "TIPO_REFEICAO")
    private TipoRefeicao tipoRefeicao;
}