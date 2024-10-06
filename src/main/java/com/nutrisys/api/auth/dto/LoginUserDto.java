package com.nutrisys.api.auth.dto;

public record LoginUserDto(
        String usuario,
        String senha
) {}