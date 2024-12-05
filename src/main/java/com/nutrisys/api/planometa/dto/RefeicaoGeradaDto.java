package com.nutrisys.api.planometa.dto;

import java.util.List;

public record RefeicaoGeradaDto(
        List<ReceitaGeradaDto> receitas
) {}