package com.nutrisys.api.paciente.dto;

import java.math.BigDecimal;

public record ListPacienteDto(
        Long id,
        String nome,
        String objetivo,
        BigDecimal peso,
        BigDecimal altura
) {}