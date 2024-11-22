package com.nutrisys.api.receita.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CalculoReceitaDto {
    private boolean gerarModoPreparo;
    private List<IngredienteDto> ingredientes;
    private String modoPreparo;
    private BigDecimal gramasPorPorcao;
}
