package com.codecool.hogwarts_potions.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private HouseType houseType;
    private PetType petType;

    @Override
    public String toString() {
        return String.format(
                "id: %s, name: %s, houseType: %s, petType: %s"
                , id
                , name
                , houseType
                , petType
        );
    }
}
