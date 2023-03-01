package com.codecool.hogwarts_potions.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PotionDTO {

    private String potionName;
    private Long studentId;
    private List<String> ingredients;

}
