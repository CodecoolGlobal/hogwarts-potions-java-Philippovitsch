package com.codecool.hogwarts_potions.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String name;

    @Override
    public String toString() {
        return String.format(
                "id: %s, name: %s"
                , id
                , name
        );
    }
}
