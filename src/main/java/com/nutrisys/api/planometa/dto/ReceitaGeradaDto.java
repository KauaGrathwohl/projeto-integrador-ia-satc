package com.nutrisys.api.planometa.dto;

import java.math.BigDecimal;

public record ReceitaGeradaDto (
        String nome,
        BigDecimal calorias,
        BigDecimal carboidratos,
        BigDecimal gordura,
        BigDecimal proteinas
) {}