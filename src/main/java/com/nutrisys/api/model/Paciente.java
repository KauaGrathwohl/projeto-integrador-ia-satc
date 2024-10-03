package com.nutrisys.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PACIENTES")
public class Paciente {

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

    @Column(name = "NOME", length = 100, nullable = false)
    private String nome;

    @Column(name = "CPF", length = 11, nullable = false)
    private String cpf;

    @Column(name = "DT_NASCIMENTO", nullable = false)
    private LocalDate dtNascimento;

    @Column(name = "STATUS", length = 1, nullable = false)
    private String status;

    @Column(name = "PESO", precision = 3, scale = 2, nullable = false)
    private BigDecimal peso;

    @Column(name = "ALTURA", precision = 3, scale = 2, nullable = false)
    private BigDecimal altura;

    @Column(name = "OBJETIVO", length = 500, nullable = false)
    private String objetivo;

    @Column(name = "RESTRICOES", length = 300, nullable = false)
    private String restricoes;

    @Column(name = "PREFERENCIAS", length = 200, nullable = false)
    private String preferencias;

    @Column(name = "DH_CRIACAO", nullable = false)
    private LocalDateTime dhCriacao;
}