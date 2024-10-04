package com.nutrisys.api.model;

import com.nutrisys.api.enums.TemaSistema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "CONFIGURACOES_GERAIS")
public class ConfiguracaoGeral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIOS", nullable = false)
    private Usuario usuario;

    @Column(name = "TEMA_SISTEMA", nullable = false)
    private TemaSistema temaSistema;
}