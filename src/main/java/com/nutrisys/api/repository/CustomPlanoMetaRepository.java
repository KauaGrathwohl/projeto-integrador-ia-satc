package com.nutrisys.api.repository;

import com.nutrisys.api.planometa.dto.ListPlanoMetaDto;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomPlanoMetaRepository {
    List<ListPlanoMetaDto> findByEntidadeAndUsuarioAndPaciente(Long entidade, Long usuario, Long paciente, Sort sort);

    Long countByEntidadeId(Long entidadeId);
}