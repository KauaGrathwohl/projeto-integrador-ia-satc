package com.nutrisys.api.repository;

import com.nutrisys.api.paciente.dto.ListPacienteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PacienteRepositoryImpl implements CustomPacienteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ListPacienteDto> findByEntidadeAndUsuarioAndNome(Long entidade, Long usuario, String nome) {
        String sql = "SELECT * FROM pacientes p WHERE p.id_entidades = ? AND p.id_usuarios = ? AND p.nome LIKE ?";
        return jdbcTemplate.query(sql, new Object[]{entidade, usuario},
            (rs, rowNum) -> new ListPacienteDto(
                    rs.getLong("id"),
                    rs.getString("nome"),
                    rs.getString("objetivo"),
                    rs.getBigDecimal("peso"),
                    rs.getBigDecimal("altura")
            ));
    }
}