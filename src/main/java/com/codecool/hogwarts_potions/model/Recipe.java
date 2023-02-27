package com.codecool.hogwarts_potions.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String name;
    @OneToOne
    private Student brewer;
    @OneToMany
    private Set<Ingredient> ingredients;

    @Override
    public String toString() {
        return String.format(
                "id: %s, name: %s, brewer: %s, ingredients: %s"
                , id
                , name
                , brewer
                , ingredients
        );
    }
}
