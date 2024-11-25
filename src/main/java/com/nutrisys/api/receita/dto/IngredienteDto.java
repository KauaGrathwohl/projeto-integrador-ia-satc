package com.nutrisys.api.receita.dto;

import java.math.BigDecimal;

public record IngredienteDto(
        String nome,
        BigDecimal quantidade,
        String unidade
) {
}