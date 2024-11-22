package com.nutrisys.api.receita.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RespostaCalculoReceitaDto {
    private BigDecimal proteinas;
    private BigDecimal gorduras;
    private BigDecimal carboidratos;
    private BigDecimal calorias;
    private String modoPreparo;
    private BigDecimal gramasPorPorcao;
}
