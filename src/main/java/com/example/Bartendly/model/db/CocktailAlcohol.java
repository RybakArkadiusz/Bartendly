package com.example.Bartendly.model.db;

import com.example.Bartendly.model.Alcohol;
import com.example.Bartendly.model.Cocktail;
import com.example.Bartendly.model.MeasurementUnit;
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
public class CocktailAlcohol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cocktail_id")
    private Cocktail cocktail;
    @ManyToOne
    @JoinColumn(name = "alcohol_id")
    private Alcohol alcohol;
    private double quantity;
    @Enumerated(EnumType.STRING)
    private MeasurementUnit measurementUnit;
}
