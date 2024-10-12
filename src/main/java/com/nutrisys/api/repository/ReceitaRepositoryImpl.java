package com.nutrisys.api.repository;

import com.nutrisys.api.receita.dto.ListReceitaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReceitaRepositoryImpl implements CustomReceitaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ListReceitaDto> findByEntidadeAndUsuario(Long entidade, Long usuario) {
        String sql = "SELECT * FROM receitas p WHERE p.id_entidades = ? AND p.id_usuarios = ?";
        return jdbcTemplate.query(sql, new Object[]{entidade, usuario},
                (rs, rowNum) -> new ListReceitaDto(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getBigDecimal("calorias"),
                        rs.getBigDecimal("carboidratos"),
                        rs.getBigDecimal("proteinas"),
                        rs.getBigDecimal("gordura")
                ));
    }
}