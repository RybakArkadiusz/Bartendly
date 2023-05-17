package com.example.Bartendly.model.repository;

import com.example.Bartendly.model.Alcohol;
import com.example.Bartendly.model.Cocktail;
import com.example.Bartendly.model.PreparationMethod;
import com.example.Bartendly.model.db.CocktailAlcohol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CocktailAlcoholRepository extends JpaRepository {
    List<CocktailAlcohol> findByAlcohol(Alcohol alcohol);
}