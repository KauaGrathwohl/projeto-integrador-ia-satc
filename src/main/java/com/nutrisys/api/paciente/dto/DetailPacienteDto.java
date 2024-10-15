package com.nutrisys.api.paciente.dto;

import com.nutrisys.api.enums.StatusPaciente;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record DetailPacienteDto(
        Long id,
        String nome,
        String cpf,
        LocalDate dtNascimento,
        StatusPaciente status,
        BigDecimal peso,
        BigDecimal altura,
        String objetivo,
        String restricoes,
        String preferencias,
        LocalDateTime dhCriacao
) {}