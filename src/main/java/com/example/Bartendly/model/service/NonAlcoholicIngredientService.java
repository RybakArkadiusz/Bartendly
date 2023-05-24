package com.example.Bartendly.model.service;

import com.example.Bartendly.model.Alcohol;
import com.example.Bartendly.model.DTO.NonAlcoholicIngredientDTO;
import com.example.Bartendly.model.NonAlcoholicIngredient;
import com.example.Bartendly.model.repository.NonAlcoholicIngredientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
public class NonAlcoholicIngredientService {
    private final NonAlcoholicIngredientRepository nonAlcoholicIngredientRepository;


    public List<NonAlcoholicIngredientDTO> findAll() {
        return nonAlcoholicIngredientRepository.findAll().stream()
                .map(this::createNonAlcoholicIngredientDTO)
                .collect(Collectors.toList());
    }

    public NonAlcoholicIngredientDTO findById(Long id) {
        NonAlcoholicIngredient nonAlcoholicIngredient = nonAlcoholicIngredientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("NonAlcoholicIngredient with id: " + id + " not found"));

        return createNonAlcoholicIngredientDTO(nonAlcoholicIngredient);
    }

    public NonAlcoholicIngredientDTO create(NonAlcoholicIngredientDTO nonAlcoholicIngredientDTO) {
        NonAlcoholicIngredient nonAlcoholicIngredient = new NonAlcoholicIngredient();
        nonAlcoholicIngredient.setName(nonAlcoholicIngredientDTO.name());
        nonAlcoholicIngredient.setType(nonAlcoholicIngredientDTO.nonAlcoholicType());

        NonAlcoholicIngredient savedNonAlcoholicIngredient = nonAlcoholicIngredientRepository.save(nonAlcoholicIngredient);

        return createNonAlcoholicIngredientDTO(savedNonAlcoholicIngredient);
    }

    public NonAlcoholicIngredientDTO update(Long id, NonAlcoholicIngredientDTO nonAlcoholicIngredientDTO) {
        NonAlcoholicIngredient nonAlcoholicIngredient = nonAlcoholicIngredientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("NonAlcoholicIngredient with id: " + id + " not found"));

        nonAlcoholicIngredient.setName(nonAlcoholicIngredientDTO.name());
        nonAlcoholicIngredient.setType(nonAlcoholicIngredientDTO.nonAlcoholicType());

        NonAlcoholicIngredient updatedNonAlcoholicIngredient = nonAlcoholicIngredientRepository.save(nonAlcoholicIngredient);

        return createNonAlcoholicIngredientDTO(updatedNonAlcoholicIngredient);
    }

    public void delete(Long id) {
        NonAlcoholicIngredient nonAlcoholicIngredient = nonAlcoholicIngredientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Non alcoholic ingredient with id: " + id + " not found"));

        nonAlcoholicIngredientRepository.delete(nonAlcoholicIngredient);
    }

    private NonAlcoholicIngredientDTO createNonAlcoholicIngredientDTO(NonAlcoholicIngredient nonAlcoholicIngredient) {
        return new NonAlcoholicIngredientDTO(nonAlcoholicIngredient.getId(), nonAlcoholicIngredient.getName(), nonAlcoholicIngredient.getType());
    }
}
