package com.nutrisys.api.repository;

import com.nutrisys.api.planometa.dto.ListPlanoMetaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class PlanoMetaRepositoryImpl implements CustomPlanoMetaRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ListPlanoMetaDto> findByEntidadeAndUsuario(Long entidade, Long usuario) {
        String sql = "SELECT * FROM planos_metas p WHERE p.id_entidades = ? AND p.id_usuarios = ?";
        return jdbcTemplate.query(sql, new Object[]{entidade, usuario},
                (rs, rowNum) -> new ListPlanoMetaDto(
                        rs.getLong("id"),
                        rs.getString("nome_plano"),
                        rs.getDate("dt_inicio_meta").toLocalDate(),
                        Objects.nonNull(rs.getDate("dt_final_meta")) ? rs.getDate("dt_final_meta").toLocalDate() : null,
                        rs.getTimestamp("dh_criacao").toLocalDateTime()
                ));
    }
}
