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
public class Room {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private Integer capacity;
    @OneToMany
    private Set<Student> residents;

    @Override
    public String toString() {
        return String.format(
                "id: %s, name: %s, capacity: %s, residents: %s"
                , id
                , name
                , capacity
                , residents
        );
    }
}
