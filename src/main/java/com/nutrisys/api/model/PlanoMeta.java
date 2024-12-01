package com.nutrisys.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@SequenceGenerator(name = "SEQ_PLANOS_METAS", sequenceName = "SEQ_PLANOS_METAS", allocationSize = 1)
@Table(name = "PLANOS_METAS")
public class PlanoMeta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PLANOS_METAS")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_ENTIDADES")
    private Entidade entidade;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIOS")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ID_PACIENTES")
    private Paciente paciente;

    @Column(name = "NOME_PLANO")
    private String nomePlano;

    @Column(name = "QTD_DIARIA_CALORIAS")
    private BigDecimal qtdDiariaCalorias;

    @Column(name = "QTD_DIARIA_PROTEINAS")
    private BigDecimal qtdDiariaProteinas;

    @Column(name = "QTD_DIARIA_CARBOIDRATOS")
    private BigDecimal qtdDiariaCarboidratos;

    @Column(name = "QTD_DIARIA_GORDURA")
    private BigDecimal qtdDiariaGordura;

    @Column(name = "DT_INICIO_META")
    private LocalDate dtInicioMeta;

    @Column(name = "DT_FINAL_META")
    private LocalDate dtFinalMeta;

    @Column(name = "DH_CRIACAO")
    private LocalDateTime dhCriacao;

    @Column(name = "DESCRICAO")
    private LocalDateTime descricao;
}
