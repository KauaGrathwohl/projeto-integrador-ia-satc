package com.nutrisys.api.auth.dto;

import com.nutrisys.api.enums.RoleName;

import java.time.LocalDate;

public record CreateUserDto (
        Long idEntidade,
        String usuario,
        String senha,
        String nome,
        LocalDate dtNascimento,
        String crn,
        String cpfCnpj,
        RoleName role
) {}