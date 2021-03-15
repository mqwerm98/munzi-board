package io.dkargo.munzi.board.entity.converter;

import io.dkargo.munzi.board.entity.Gender;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;

public class GenderConverter implements AttributeConverter<Gender, String> {


    @Override
    public String convertToDatabaseColumn(Gender gender) {
        if (gender == null) return null;
        return gender.getValue();
    }

    @Override
    public Gender convertToEntityAttribute(String value) {
        if (value == null) return null;

        return Stream.of(Gender.values())
                .filter(g -> g.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
