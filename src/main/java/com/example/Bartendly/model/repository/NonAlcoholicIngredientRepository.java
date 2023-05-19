package com.example.Bartendly.model.repository;

import com.example.Bartendly.model.Alcohol;
import com.example.Bartendly.model.NonAlcoholicIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NonAlcoholicIngredientRepository extends JpaRepository<NonAlcoholicIngredient, Long> {

}
