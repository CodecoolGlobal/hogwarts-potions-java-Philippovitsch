package com.codecool.hogwarts_potions.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PotionDTO {

    private String potionName;
    private Long studentId;
    private List<String> ingredients;

}
