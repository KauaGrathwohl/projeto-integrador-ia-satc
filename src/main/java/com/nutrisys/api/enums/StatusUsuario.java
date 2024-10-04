package com.nutrisys.api.enums;

public enum StatusUsuario {

    ATIVO(0, "Ativo"),
    DESATIVADO(1, "Desativado");

    private Integer value;
    private String description;

    StatusUsuario(Integer value, String description) {
        this.value = value;
        this.description = description;
    }
}
