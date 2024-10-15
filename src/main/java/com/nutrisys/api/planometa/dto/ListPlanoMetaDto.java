package com.nutrisys.api.planometa.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ListPlanoMetaDto(
    Long id,
    String nome,
    LocalDate dtInicioMeta,
    LocalDate dtFinalMeta,
    LocalDateTime dhCriacao
) {}