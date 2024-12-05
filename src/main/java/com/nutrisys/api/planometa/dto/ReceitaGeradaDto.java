package com.nutrisys.api.planometa.dto;

import java.math.BigDecimal;

public record ReceitaGeradaDto (
        Long id,
        String nome,
        BigDecimal calorias,
        BigDecimal carboidratos,
        BigDecimal gordura,
        BigDecimal proteinas
) {}