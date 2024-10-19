package com.nutrisys.api.enums;

public enum TemaSistema {
    DARK(0, "Dark Theme"),
    LIGHT(1, "Light Theme");

    private Integer value;
    private String description;

    TemaSistema(Integer value, String description) {
        this.value = value;
        this.description = description;
    }
}
