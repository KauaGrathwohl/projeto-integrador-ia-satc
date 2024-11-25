package com.nutrisys.api.receita.dto;

import java.math.BigDecimal;

public record RespostaCalculoReceitaDto (
        MacronutrientesDto macronutrientes,
        String modoPreparo,
        BigDecimal gramasPorPorcao
) {
}