package com.nutrisys.api.receita.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class IngredienteDto {
    private String nome;
    private BigDecimal quantidade;
    private String unidade;
}
