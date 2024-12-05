package com.nutrisys.api.receita.dto;

import java.math.BigDecimal;
import java.util.List;

public record CalculoReceitaDto (
        boolean gerarModoPreparo,
        List<IngredienteDto> ingredientes,
        String modoPreparo,
        BigDecimal gramasPorPorcao
) {
}