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
    @JoinColumn(name = "ID_ENTIDADES")
    private Entidade entidade;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIOS")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ID_PACIENTES")
    private Paciente paciente;

    @Column(name = "QTD_DIARIA_CALORIAS")
    private Integer qtdDiariaCalorias;

    @Column(name = "QTD_DIARIA_PROTEINAS")
    private Integer qtdDiariaProteinas;

    @Column(name = "QTD_DIARIA_CARBOIDRATOS")
    private Integer qtdDiariaCarboidratos;

    @Column(name = "DT_INICIO_META")
    private LocalDate dtInicioMeta;

    @Column(name = "DT_FINAL_META")
    private LocalDate dtFinalMeta;

    @Column(name = "DH_CRIACAO")
    private LocalDateTime dhCriacao;
}