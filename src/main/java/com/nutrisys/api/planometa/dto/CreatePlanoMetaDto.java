package com.nutrisys.api.planometa.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreatePlanoMetaDto(
        String nomePlano,
        LocalDate dtInicial,
        BigDecimal qtdDiariaCalorias,
        BigDecimal qtdDiariaCarboidratos,
        BigDecimal qtdDiariaGordura,
        BigDecimal qtdDiariaProteina
) {}