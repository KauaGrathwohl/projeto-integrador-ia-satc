package com.nutrisys.api.receita.dto;

import com.nutrisys.api.enums.TipoRefeicao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreatedReceitaDto(
        Long id,
        String nome,
        BigDecimal gramas,
        BigDecimal calorias,
        BigDecimal proteinas,
        BigDecimal carboidratos,
        BigDecimal gordura,
        TipoRefeicao tipoRefeicao,
        String descricao,
        LocalDateTime dhCriacao
) {
}
