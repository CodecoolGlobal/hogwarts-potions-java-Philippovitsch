package com.codecool.hogwarts_potions.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PotionDTO {

    private final String potionName;
    private final Long studentId;
    private final List<String> ingredients;

    public PotionDTO(String potionName, Long studentId, List<String> ingredients) {
        this.potionName = potionName;
        this.studentId = studentId;
        this.ingredients = ingredients;
    }

}
