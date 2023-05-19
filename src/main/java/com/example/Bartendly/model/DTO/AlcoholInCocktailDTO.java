package com.example.Bartendly.model.DTO;

import com.example.Bartendly.model.Alcohol;
import com.example.Bartendly.model.MeasurementUnit;

public record AlcoholInCocktailDTO(Alcohol alcohol, double quantity, MeasurementUnit measurementUnit) {
}
