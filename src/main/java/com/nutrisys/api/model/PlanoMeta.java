package com.nutrisys.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "PLANOS_METAS")
public class PlanoMeta {

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
    @JoinColumn(name = "ID_PACIENTES", nullable = false)
    private Paciente paciente;

    @Column(name = "QTD_DIARIA_CALORIAS", nullable = false)
    private Integer qtdDiariaCalorias;

    @Column(name = "QTD_DIARIA_PROTEINAS", nullable = false)
    private Integer qtdDiariaProteinas;

    @Column(name = "QTD_DIARIA_CARBOIDRATOS", nullable = false)
    private Integer qtdDiariaCarboidratos;

    @Column(name = "DT_INICIO_META", nullable = false)
    private LocalDate dtInicioMeta;

    @Column(name = "DT_FINAL_META")
    private LocalDate dtFinalMeta;

    @Column(name = "DH_CRIACAO", nullable = false)
    private LocalDateTime dhCriacao;
}