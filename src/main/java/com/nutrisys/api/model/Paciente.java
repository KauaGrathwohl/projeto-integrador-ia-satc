package com.nutrisys.api.model;

import com.nutrisys.api.enums.StatusPaciente;
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
    @JoinColumn(name = "ID_ENTIDADES")
    private Entidade entidade;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIOS")
    private Usuario usuario;

    @Column(name = "NOME", length = 100)
    private String nome;

    @Column(name = "CPF", length = 11)
    private String cpf;

    @Column(name = "DT_NASCIMENTO")
    private LocalDate dtNascimento;

    @Column(name = "STATUS")
    private StatusPaciente status;

    @Column(name = "PESO")
    private BigDecimal peso;

    @Column(name = "ALTURA")
    private BigDecimal altura;

    @Column(name = "OBJETIVO")
    private String objetivo;

    @Column(name = "RESTRICOES")
    private String restricoes;

    @Column(name = "PREFERENCIAS")
    private String preferencias;

    @Column(name = "DH_CRIACAO")
    private LocalDateTime dhCriacao;
}