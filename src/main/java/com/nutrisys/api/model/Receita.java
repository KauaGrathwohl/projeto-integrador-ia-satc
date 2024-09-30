package com.nutrisys.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "RECEITAS")
public class Receita {

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

    @Column(name = "NOME", length = 50, nullable = false)
    private String nome;

    @Column(name = "CALORIAS", nullable = false)
    private Integer calorias;

    @Column(name = "PROTEINAS", nullable = false)
    private Integer proteinas;

    @Column(name = "CARBOIDRATOS", nullable = false)
    private Integer carboidratos;

    @Column(name = "TIPO_REFEICAO", length = 1, nullable = false)
    private String tipoRefeicao;

    @Column(name = "DESCRICAO", length = 1000, nullable = false)
    private String descricao;

    @Column(name = "DH_CRIACAO", nullable = false)
    private LocalDate dhCriacao;

}