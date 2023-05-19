package com.example.Bartendly.model.service;

import com.example.Bartendly.model.*;
import com.example.Bartendly.model.DTO.CocktailDTO;
import com.example.Bartendly.model.db.CocktailAlcohol;
import com.example.Bartendly.model.db.CocktailFlavourProfile;
import com.example.Bartendly.model.db.CocktailNonAlcoholicIngredient;
import com.example.Bartendly.model.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
public class CocktailService {
    private final CocktailRepository cocktailRepository;
    private final CocktailAlcoholRepository cocktailAlcoholRepository;
    private final CocktailFlavourProfileRepository cocktailFlavourProfileRepository;
    private final CocktailNonAlcoholicIngredientRepository cocktailNonAlcoholicIngredientRepository;
    private final AlcoholRepository alcoholRepository;
    private final NonAlcoholicIngredientRepository nonAlcoholicIngredientRepository;


    public List<CocktailDTO> findCocktailsByCriteria(List<Alcohol> alcohols, List<FlavourProfile> flavours, List<NonAlcoholicIngredient> nonAlcoholicIngredients, PreparationMethod method) {
        List<Long> alcoholIds = alcohols.stream().map(Alcohol::getId).collect(Collectors.toList());
        List<String> flavourNames = flavours.stream().map(Enum::name).collect(Collectors.toList());
        List<Long> nonAlcoholicIngredientIds = nonAlcoholicIngredients.stream().map(NonAlcoholicIngredient::getId).collect(Collectors.toList());

        List<Cocktail> cocktails = cocktailRepository.findCocktailsByCriteria(alcoholIds, alcoholIds.size(), flavourNames, flavourNames.size(), nonAlcoholicIngredientIds, nonAlcoholicIngredientIds.size(), method.name());



        return cocktails.stream()
                .map(this::createCocktailDTO)
                .collect(Collectors.toList());
    }

    public List<CocktailDTO> findAll(){
        List<Cocktail> cocktails = cocktailRepository.findAll();
        return cocktails.stream()
                .map(this::createCocktailDTO)
                .collect(Collectors.toList());

    }

    public CocktailDTO findById(long id){
        Optional<Cocktail> cocktail = cocktailRepository.findById(id);
        if(cocktail.isPresent()){
            return createCocktailDTO(cocktail.get());
        }else{
            throw new EntityNotFoundException("Cocktail with id: "+ id + "not found!");
        }
    }






    private CocktailDTO createCocktailDTO(Cocktail cocktail) {

        List<Alcohol> alcohols = cocktailAlcoholRepository.findByCocktail(cocktail)
                .stream()
                .map(CocktailAlcohol::getAlcohol)
                .collect(Collectors.toList());

        List<NonAlcoholicIngredient> nonAlcoholicIngredients = cocktailNonAlcoholicIngredientRepository.findByCocktail(cocktail)
                .stream()
                .map(CocktailNonAlcoholicIngredient::getNonAlcoholicIngredient)
                .collect(Collectors.toList());

        List<FlavourProfile> flavourProfiles = cocktailFlavourProfileRepository.findByCocktail(cocktail)
                .stream()
                .map(CocktailFlavourProfile::getFlavourProfile)
                .collect(Collectors.toList());

        return new CocktailDTO(cocktail.getId(), cocktail.getName(), cocktail.getPrepMethod(), cocktail.getRecipe(),
                alcohols, nonAlcoholicIngredients, flavourProfiles);
    }

//    private Cocktail convertToEntity(CocktailDTO cocktailDTO){
//        Cocktail cocktail = new Cocktail(cocktailDTO.id(),cocktailDTO.name(),cocktailDTO.preparationMethod(),cocktailDTO.recipe());
//        cocktailRepository.save(cocktail);
//        cocktailDTO.alcohols().stream()
//                .map(alcohol->{
//                    cocktailAlcoholRepository.save(new CocktailAlcohol(cocktail,alcohol))
//                })
//    }
}
