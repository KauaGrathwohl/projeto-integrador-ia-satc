package com.nutrisys.api.repository;

import com.nutrisys.api.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long>, CustomReceitaRepository {
}