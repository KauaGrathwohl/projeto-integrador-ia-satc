package com.nutrisys.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "USUARIOS")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "USUARIOS_ENTIDADES",
            joinColumns = @JoinColumn(name = "ID_USUARIOS"),
            inverseJoinColumns = @JoinColumn(name = "ID_ENTIDADES")
    )
    private Set<Entidade> entidades = new HashSet<>();

    @Column(name = "USUARIO")
    private String usuario;

    @Column(name = "SENHA")
    private String senha;

    @Column(name = "NOME", length = 100)
    private String nome;

    @Column(name = "DT_NASCIMENTO")
    private LocalDate dtNascimento;

    @Column(name = "CRN", length = 10)
    private String crn;

    @Column(name = "CPF_CNPJ", length = 14)
    private String cpfCnpj;

    @Column(name = "STATUS", length = 1)
    private String status;

    @Column(name = "DH_CRIACAO")
    private LocalDateTime dhCriacao;
}