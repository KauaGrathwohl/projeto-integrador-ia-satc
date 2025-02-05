package com.nutrisys.api.planometa.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record CreatedPlanoMetaDto(
        Long id,
        Long idPaciente,
        String nomePlano,
        LocalDate dtInicial,
        BigDecimal qtdDiariaCalorias,
        BigDecimal qtdDiariaCarboidratos,
        BigDecimal qtdDiariaGordura,
        BigDecimal qtdDiariaProteina,
        LocalDateTime dhCriacao
) {}