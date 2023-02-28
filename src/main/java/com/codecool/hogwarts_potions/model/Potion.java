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
public class Potion {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String name;
    @OneToOne
    private Student brewer;
    @ManyToMany
    private Set<Ingredient> ingredients;
    private BrewingStatus brewingStatus;
    @OneToOne
    private Recipe recipe;

    @Override
    public String toString() {
        return String.format(
                "id: %s, name: %s, brewer: %s, ingredients: %s, brewingStatus: %s, recipe: %s"
                , id
                , name
                , brewer
                , ingredients
                , brewingStatus
                , recipe
        );
    }
}
