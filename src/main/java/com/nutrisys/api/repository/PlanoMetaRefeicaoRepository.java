package com.nutrisys.api.repository;

import com.nutrisys.api.model.PlanoMetaRefeicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanoMetaRefeicaoRepository extends JpaRepository<PlanoMetaRefeicao, Long> {
}
