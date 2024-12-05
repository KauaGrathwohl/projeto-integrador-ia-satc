package com.nutrisys.api.receita.dto;

import java.math.BigDecimal;

public record MacronutrientesDto(
        BigDecimal proteinas,
        BigDecimal gorduras,
        BigDecimal carboidratos,
        BigDecimal calorias
) {
}