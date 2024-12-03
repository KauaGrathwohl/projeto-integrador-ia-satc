package com.nutrisys.api.planometa.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FindPlanoMetaDto(
        LocalDate dtInicioMeta,
        LocalDate dtFinalMeta,
        BigDecimal qtdDiariaCalorias,
        BigDecimal qtdDiariaProteinas,
        BigDecimal qtdDiariaCarboidratos,
        BigDecimal qtdDiariaGordura) {
}
