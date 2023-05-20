package com.example.Bartendly.model.controller;

import com.example.Bartendly.model.DTO.NonAlcoholicIngredientDTO;
import com.example.Bartendly.model.service.NonAlcoholicIngredientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nonAlcoholicIngredient")
@AllArgsConstructor
public class NonAlcoholicIngredientController {

    private final NonAlcoholicIngredientService nonAlcoholicIngredientService;


    @GetMapping
    public List<NonAlcoholicIngredientDTO> findAll() {
        return nonAlcoholicIngredientService.findAll();
    }

    @GetMapping("/{id}")
    public NonAlcoholicIngredientDTO findById(@PathVariable Long id) {
        return nonAlcoholicIngredientService.findById(id);
    }

    @PostMapping
    public ResponseEntity<NonAlcoholicIngredientDTO> create(@RequestBody NonAlcoholicIngredientDTO nonAlcoholicIngredientDTO) {
        return new ResponseEntity<>(nonAlcoholicIngredientService.create(nonAlcoholicIngredientDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public NonAlcoholicIngredientDTO update(@PathVariable Long id, @RequestBody NonAlcoholicIngredientDTO nonAlcoholicIngredientDTO) {
        return nonAlcoholicIngredientService.update(id, nonAlcoholicIngredientDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        nonAlcoholicIngredientService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
