package com.nutrisys.api.repository;

import com.nutrisys.api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsuario(String email);

    Optional<Usuario> findByCpfCnpjAndEntidadesId(String cpfCnpj, Long entidade);

    Optional<Usuario> findByUsuarioAndEntidadesId(String email, Long entidade);
}