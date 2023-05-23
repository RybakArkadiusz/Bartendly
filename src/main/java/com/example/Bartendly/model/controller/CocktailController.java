package com.example.Bartendly.model.controller;

import com.example.Bartendly.model.Alcohol;
import com.example.Bartendly.model.DTO.CocktailDTO;
import com.example.Bartendly.model.FlavourProfile;
import com.example.Bartendly.model.NonAlcoholicIngredient;
import com.example.Bartendly.model.PreparationMethod;
import com.example.Bartendly.model.repository.AlcoholRepository;
import com.example.Bartendly.model.repository.NonAlcoholicIngredientRepository;
import com.example.Bartendly.model.service.CocktailService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cocktails")
@AllArgsConstructor
public class CocktailController {

    private final CocktailService cocktailService;
    private final AlcoholRepository alcoholRepository;
    private final NonAlcoholicIngredientRepository nonAlcoholicIngredientRepository;

    @GetMapping("/search")//working
    public ResponseEntity<List<CocktailDTO>> findByCriteria(
            @RequestParam(required = false) List<Long> alcohols,
            @RequestParam(required = false) List<String> flavours,
            @RequestParam(required = false) List<Long> nonAlcoholicIngredients) {

        List<Alcohol> alcoholList = new ArrayList<Alcohol>();
        if(alcohols != null) {
            alcoholList = alcohols.stream()
                    .map(id -> alcoholRepository.findById(id)
                            .orElseThrow(() -> new EntityNotFoundException("Alcohol with id: " + id + " not found")))
                    .collect(Collectors.toList());
        }

        List<FlavourProfile> flavourList = new ArrayList<FlavourProfile>();
        if(flavours != null) {
            flavourList = flavours.stream()
                    .map(FlavourProfile::valueOf)
                    .collect(Collectors.toList());
        }


        List<NonAlcoholicIngredient> nonAlcoholicIngredientList = null;
        if(nonAlcoholicIngredients !=null) {
            nonAlcoholicIngredientList = nonAlcoholicIngredients.stream()
                    .map(id -> nonAlcoholicIngredientRepository.findById(id)
                            .orElseThrow(() -> new EntityNotFoundException("Non-Alcoholic Ingredient with id: " + id + " not found")))
                    .collect(Collectors.toList());
        }


        return ResponseEntity.ok(cocktailService.findCocktailsByCriteria(alcoholList, flavourList, nonAlcoholicIngredientList));
    }


    @GetMapping//working
    public ResponseEntity<List<CocktailDTO>> findAll() {
        return ResponseEntity.ok(cocktailService.findAll());
    }

    @GetMapping("/{id}")//working
    public ResponseEntity<CocktailDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(cocktailService.findById(id));
    }

    @PostMapping//working
    public ResponseEntity<CocktailDTO> create(@RequestBody CocktailDTO cocktailDTO) {
        return ResponseEntity.ok(cocktailService.create(cocktailDTO));
    }

    @PutMapping("/{id}")//working
    public ResponseEntity<CocktailDTO> update(@PathVariable Long id, @RequestBody CocktailDTO cocktailDTO) {
        return ResponseEntity.ok(cocktailService.update(id, cocktailDTO));
    }

    @DeleteMapping("/{id}")//working
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cocktailService.delete(id);
        return ResponseEntity.noContent().build();
    }
}