package com.nutrisys.api.enums;

public enum TipoRefeicao {

    CAFE_DA_MANHA(0, "Café da manhã"),
    ALMOCO(1, "Almoço"),
    CAFE_DA_TARDE(2, "Café da tarde"),
    JANTAR(3, "Jantar");

    private final int value;
    private final String description;

    TipoRefeicao(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static TipoRefeicao fromValue(int value) {
        for (TipoRefeicao tipo : TipoRefeicao.values()) {
            if (tipo.getValue() == value) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("TipoRefeicao inválido: " + value);
    }
}
