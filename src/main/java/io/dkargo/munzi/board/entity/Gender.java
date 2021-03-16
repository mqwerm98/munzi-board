package io.dkargo.munzi.board.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
public enum Gender {
    M("Male"),
    F("Female");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}
