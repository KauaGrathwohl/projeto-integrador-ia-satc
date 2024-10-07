package com.nutrisys.api.model;

import com.nutrisys.api.enums.TipoRefeicao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@SequenceGenerator(name = "SEQ_RECEITAS", sequenceName = "SEQ_RECEITAS", allocationSize = 1)
@Table(name = "RECEITAS")
public class Receita {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RECEITAS")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_ENTIDADES")
    private Entidade entidade;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIOS")
    private Usuario usuario;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "GRAMAS")
    private BigDecimal gramas;

    @Column(name = "CALORIAS")
    private BigDecimal calorias;

    @Column(name = "PROTEINAS")
    private BigDecimal proteinas;

    @Column(name = "CARBOIDRATOS")
    private BigDecimal carboidratos;

    @Column(name = "TIPO_REFEICAO")
    private TipoRefeicao tipoRefeicao;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "DH_CRIACAO")
    private LocalDate dhCriacao;

}