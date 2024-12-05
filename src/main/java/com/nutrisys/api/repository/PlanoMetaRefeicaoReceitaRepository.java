package com.nutrisys.api.repository;

import com.nutrisys.api.model.PlanoMetaRefeicaoReceita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanoMetaRefeicaoReceitaRepository extends JpaRepository<PlanoMetaRefeicaoReceita, Long> {
}
