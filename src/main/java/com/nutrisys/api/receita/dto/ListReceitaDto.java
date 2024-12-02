package com.nutrisys.api.receita.dto;

import java.math.BigDecimal;

public record ListReceitaDto(
        Long id,
        String nome,
        BigDecimal calorias,
        BigDecimal carboidratos,
        BigDecimal proteinas,
        BigDecimal gordura,
        Integer tipoRefeicao
) {
}