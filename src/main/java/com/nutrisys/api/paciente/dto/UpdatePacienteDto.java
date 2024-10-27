package com.nutrisys.api.paciente.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdatePacienteDto(
        Long id,
        String nome,
        String cpf,
        LocalDate dtNascimento,
        BigDecimal peso,
        BigDecimal altura,
        String objetivo,
        String restricoes,
        String preferencias
) {}