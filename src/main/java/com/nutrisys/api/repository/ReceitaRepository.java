package com.nutrisys.api.repository;

import com.nutrisys.api.model.Receita;
import com.nutrisys.api.enums.TipoRefeicao;
import com.nutrisys.api.receita.dto.ListReceitaDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    List<Receita> findByTipoRefeicao(TipoRefeicao tipoRefeicao);

    @Query("SELECT r FROM Receita r WHERE r.entidade.id = :entidadeId AND r.usuario.id = :usuarioId AND r.nome LIKE %:filtro%")
    List<ListReceitaDto> findByEntidadeAndUsuarioAndFiltro(Long entidadeId, Long usuarioId, String filtro);

    @Query("SELECT COUNT(r) FROM Receita r WHERE r.entidade.id = :entidadeId")
    long countByEntidadeId(Long entidadeId);
}
