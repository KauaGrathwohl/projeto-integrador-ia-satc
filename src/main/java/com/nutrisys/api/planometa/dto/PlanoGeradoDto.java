package com.nutrisys.api.planometa.dto;

import java.math.BigDecimal;

public record PlanoGeradoDto (
        BigDecimal caloriasDiarias,
        BigDecimal carboidratosDiarios,
        BigDecimal gorduraDiaria,
        BigDecimal proteinasDiarias,
        RefeicaoGeradaDto cafeDaManha,
        RefeicaoGeradaDto almoco,
        RefeicaoGeradaDto cafeDaTarde,
        RefeicaoGeradaDto jantar
)
{}