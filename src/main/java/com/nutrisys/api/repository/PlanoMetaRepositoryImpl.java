package com.nutrisys.api.repository;

import com.nutrisys.api.planometa.dto.ListPlanoMetaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class PlanoMetaRepositoryImpl implements CustomPlanoMetaRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ListPlanoMetaDto> findByEntidadeAndUsuarioAndPaciente(Long entidade, Long usuario, Long paciente, Sort sort) {
        String sql = "SELECT * FROM planos_metas p WHERE p.id_entidades = ? AND p.id_usuarios = ? AND p.id_pacientes = ? ORDER BY p.id DESC";
        return jdbcTemplate.query(sql, new Object[]{entidade, usuario, paciente},
            (rs, rowNum) -> new ListPlanoMetaDto(
                rs.getLong("id"),
                rs.getString("nome_plano"),
                rs.getDate("dt_inicio_meta").toLocalDate(),
                Objects.nonNull(rs.getDate("dt_final_meta")) ? rs.getDate("dt_final_meta").toLocalDate() : null,
                rs.getTimestamp("dh_criacao").toLocalDateTime()
            ));
    }

    @Override
    public Long countByEntidadeId(Long entidadeId) {
        String sql = "SELECT COUNT(*) FROM planos_metas WHERE id_entidades = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{entidadeId}, Long.class);
    }
}
