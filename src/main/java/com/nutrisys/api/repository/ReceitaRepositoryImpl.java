package com.nutrisys.api.repository;

import com.nutrisys.api.receita.dto.ListReceitaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ReceitaRepositoryImpl implements CustomReceitaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ListReceitaDto> findByEntidadeAndUsuarioAndFiltro(Long entidade, Long usuario, String filtro) {
        StringBuilder sql = new StringBuilder("SELECT * FROM receitas p WHERE p.id_entidades = ? AND p.id_usuarios = ?");
        List<Object> paramValues = new ArrayList<>();
        paramValues.add(entidade);
        paramValues.add(usuario);
        if (!Objects.isNull(filtro) && !filtro.isEmpty()) {
            sql.append(" AND p.nome LIKE ?");
            paramValues.add("%" + filtro + "%");
        }

        return jdbcTemplate.query(sql.toString(), paramValues.toArray(),
                (rs, rowNum) -> new ListReceitaDto(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getBigDecimal("calorias"),
                        rs.getBigDecimal("carboidratos"),
                        rs.getBigDecimal("proteinas"),
                        rs.getBigDecimal("gordura"),
                        rs.getInt("tipo_refeicao")
                ));
    }

    @Override
    public Long countByEntidadeId(Long entidadeId) {
        String sql = "SELECT COUNT(*) FROM receitas WHERE id_entidades = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{entidadeId}, Long.class);
    }
}