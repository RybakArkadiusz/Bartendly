package com.example.Bartendly.model.repository;

import com.example.Bartendly.model.Cocktail;
import com.example.Bartendly.model.PreparationMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CocktailRepository extends JpaRepository {
    List<Cocktail> findByPreparationMethod(PreparationMethod preparationMethod);
}
