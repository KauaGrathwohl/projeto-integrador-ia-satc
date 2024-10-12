package com.nutrisys.api.receita.dto;

import com.nutrisys.api.enums.TipoRefeicao;

import java.math.BigDecimal;

public record CreateReceitaDto(
        String nome,
        BigDecimal gramas,
        BigDecimal calorias,
        BigDecimal proteinas,
        BigDecimal carboidratos,
        BigDecimal gordura,
        TipoRefeicao tipoRefeicao,
        String descricao
) {
}