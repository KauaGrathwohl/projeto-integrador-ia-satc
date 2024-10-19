package com.nutrisys.api.enums;

public enum StatusPaciente {

    ATIVO(0, "Ativo"),
    DESATIVADO(1, "Desativado");

    private Integer value;
    private String description;

    StatusPaciente(Integer value, String description) {
        this.value = value;
        this.description = description;
    }
}