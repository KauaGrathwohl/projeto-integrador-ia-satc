package com.nutrisys.api.repository;

import com.nutrisys.api.paciente.dto.ListPacienteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PacienteRepositoryImpl implements CustomPacienteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ListPacienteDto> findByEntidadeAndUsuarioAndFiltro(Long entidade, Long usuario, String filtro) {
        StringBuilder sql = new StringBuilder("SELECT * FROM pacientes p WHERE p.id_entidades = ? AND p.id_usuarios = ?");
        List<Object> paramValues = new ArrayList<>();
        paramValues.add(entidade);
        paramValues.add(usuario);
        if (!filtro.isEmpty()) {
            sql.append(" AND p.nome LIKE ?");
            paramValues.add("%" + filtro + "%");
        }

        return jdbcTemplate.query(sql.toString(), paramValues.toArray(),
            (rs, rowNum) -> new ListPacienteDto(
                    rs.getLong("id"),
                    rs.getString("nome"),
                    rs.getString("objetivo"),
                    rs.getBigDecimal("peso"),
                    rs.getBigDecimal("altura")
            ));
    }
}