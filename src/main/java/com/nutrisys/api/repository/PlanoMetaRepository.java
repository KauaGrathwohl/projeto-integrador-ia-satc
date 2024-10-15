package com.nutrisys.api.repository;

import com.nutrisys.api.model.PlanoMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanoMetaRepository extends JpaRepository<PlanoMeta, Long>, CustomPlanoMetaRepository {
}