package com.nutrisys.api.model;

import com.nutrisys.api.enums.TipoRefeicao;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "RECEITAS")
public class Receita {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RECEITAS")
    @SequenceGenerator(name = "SEQ_RECEITAS", sequenceName = "SEQ_RECEITAS", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_ENTIDADES", nullable = false)
    private Entidade entidade;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIOS", nullable = false)
    private Usuario usuario;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "GRAMAS", precision = 10, scale = 2)
    private BigDecimal gramas;

    @Column(name = "CALORIAS", precision = 10, scale = 2)
    private BigDecimal calorias;

    @Column(name = "PROTEINAS", precision = 10, scale = 2)
    private BigDecimal proteinas;

    @Column(name = "CARBOIDRATOS", precision = 10, scale = 2)
    private BigDecimal carboidratos;

    @Column(name = "GORDURA", precision = 10, scale = 2)
    private BigDecimal gordura;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_REFEICAO", nullable = false)
    private TipoRefeicao tipoRefeicao;

    @Column(name = "DESCRICAO", length = 500)
    private String descricao;

    @Column(name = "DH_CRIACAO", nullable = false)
    private LocalDateTime dhCriacao;
}
