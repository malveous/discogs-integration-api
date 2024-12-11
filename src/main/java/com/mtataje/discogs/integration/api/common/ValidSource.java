package com.mtataje.discogs.integration.api.common;

import lombok.Getter;

@Getter
public enum ValidSource {
    API("api"),
    DATABASE("database");

    private String value;

    ValidSource(String value) {
        this.value = value;
    }
}
