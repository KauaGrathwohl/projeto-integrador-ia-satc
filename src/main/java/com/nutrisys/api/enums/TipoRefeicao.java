package com.nutrisys.api.enums;

public enum TipoRefeicao {

    CAFE_DA_MANHA(0, "Café da manhã"),
    ALMOCO(1, "Almoço"),
    CAFE_DA_TARDE(2, "Café da tarde"),
    JANTAR(3, "Jantar");

    private Integer value;
    private String description;

    TipoRefeicao(Integer value, String description) {
        this.value = value;
        this.description = description;
    }
}