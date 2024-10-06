package com.nutrisys.api.auth.dto;

import com.nutrisys.api.model.Role;

import java.util.List;

public record RecoveryUserDto(
        Long id,
        String usuario,
        List<Role> roles
) {}