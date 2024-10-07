package com.nutrisys.api.model;

import com.nutrisys.api.enums.StatusUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@SequenceGenerator(name = "SEQ_USUARIOS", sequenceName = "SEQ_USUARIOS", allocationSize = 1)
@Table(name = "USUARIOS")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USUARIOS")
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

    @Column(name = "NOME")
    private String nome;

    @Column(name = "DT_NASCIMENTO")
    private LocalDate dtNascimento;

    @Column(name = "CRN")
    private String crn;

    @Column(name = "CPF_CNPJ")
    private String cpfCnpj;

    @Column(name = "STATUS")
    private StatusUsuario status = StatusUsuario.ATIVO;

    @Column(name = "DH_CRIACAO")
    private LocalDateTime dhCriacao;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "usuarios_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;
}