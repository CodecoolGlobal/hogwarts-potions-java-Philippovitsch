package com.codecool.hogwarts_potions.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PotionDTO {

    private final Long studentId;
    private final List<String> ingredients;

    public PotionDTO(Long studentId, List<String> ingredients) {
        this.studentId = studentId;
        this.ingredients = ingredients;
    }

}
