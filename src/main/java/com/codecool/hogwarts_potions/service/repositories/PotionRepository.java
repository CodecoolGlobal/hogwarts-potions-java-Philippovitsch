package com.codecool.hogwarts_potions.service.repositories;

import com.codecool.hogwarts_potions.model.Potion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PotionRepository extends JpaRepository<Potion, Long> {

}
