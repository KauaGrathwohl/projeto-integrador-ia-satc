package com.nutrisys.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "ENTIDADES")
public class Entidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToMany(mappedBy = "entidades")
    private Set<Usuario> usuarios = new HashSet<>();

    @Column(name = "NOME_CONSULTORIO", length = 100, nullable = false)
    private String nomeConsultorio;

    @Column(name = "CNPJ", length = 100, nullable = false)
    private String cnpj;

    @Column(name = "LOCALIZACAO", length = 150, nullable = false)
    private String localizacao;
}