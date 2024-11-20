package com.nutrisys.api.repository;

import com.nutrisys.api.receita.dto.ListReceitaDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomReceitaRepository {

    List<ListReceitaDto> findByEntidadeAndUsuarioAndFiltro(Long entidade, Long usuario, String filtro);

    Long countByEntidadeId(Long entidadeId);
}