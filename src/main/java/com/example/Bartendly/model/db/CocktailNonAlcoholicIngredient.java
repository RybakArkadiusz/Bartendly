package com.example.Bartendly.model.db;

import com.example.Bartendly.model.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CocktailNonAlcoholicIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cocktail_id")
    private Cocktail cocktail;
    @ManyToOne
    @JoinColumn(name = "non_alcoholic_ingredient_id")
    private NonAlcoholicIngredient nonAlcoholicIngredient;
    private double quantity;
    @Enumerated(EnumType.STRING)
    private MeasurementUnit measurementUnit;
}
