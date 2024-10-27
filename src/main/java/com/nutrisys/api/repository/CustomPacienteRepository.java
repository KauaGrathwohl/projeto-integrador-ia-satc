package com.nutrisys.api.repository;

import com.nutrisys.api.paciente.dto.ListPacienteDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomPacienteRepository {
    List<ListPacienteDto> findByEntidadeAndUsuarioAndFiltro(Long entidade, Long usuario, String filtro);
}