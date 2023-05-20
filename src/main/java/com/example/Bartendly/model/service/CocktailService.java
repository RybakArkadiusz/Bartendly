package com.example.Bartendly.model.service;

import com.example.Bartendly.model.*;
import com.example.Bartendly.model.DTO.AlcoholInCocktailDTO;
import com.example.Bartendly.model.DTO.CocktailDTO;
import com.example.Bartendly.model.DTO.NonAlcoholicIngredientInCocktailDTO;
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
            throw new EntityNotFoundException("Cocktail with id: "+ id + " not found");
        }
    }

    public CocktailDTO create(CocktailDTO cocktailDTO) { //using for cause streams too complicated
        //creating new Cocktail
        Cocktail cocktail = new Cocktail();
        cocktail.setName(cocktailDTO.name());
        cocktail.setPreparationMethod(cocktailDTO.preparationMethod());
        cocktail.setRecipe(cocktailDTO.recipe());

        Cocktail savedCocktail = cocktailRepository.save(cocktail);

        //adding alcohols to cocktail
        for(AlcoholInCocktailDTO aic : cocktailDTO.alcoholIngredients()) {
            CocktailAlcohol cocktailAlcohol = new CocktailAlcohol();
            cocktailAlcohol.setCocktail(savedCocktail);
            cocktailAlcohol.setAlcohol(alcoholRepository.findById(aic.alcohol().getId())
                    .orElseThrow(() ->new EntityNotFoundException("Alcohol with id: "+ aic.alcohol().getId() + " not found")));
            cocktailAlcohol.setQuantity(aic.quantity());
            cocktailAlcohol.setMeasurementUnit(aic.measurementUnit());

            cocktailAlcoholRepository.save(cocktailAlcohol);
        }

        //adding non-alcoholic ingredients to cocktail
        for(NonAlcoholicIngredientInCocktailDTO nai : cocktailDTO.nonAlcoholicIngredients()) {
            CocktailNonAlcoholicIngredient nonAlcoholic = new CocktailNonAlcoholicIngredient();
            nonAlcoholic.setCocktail(savedCocktail);
            nonAlcoholic.setNonAlcoholicIngredient(nonAlcoholicIngredientRepository.findById(nai.nonAlcoholicIngredient().getId())
                    .orElseThrow(() ->new EntityNotFoundException("non-alcoholic ingredient with id: "+ nai.nonAlcoholicIngredient().getId() + " not found")));
            nonAlcoholic.setQuantity(nai.quantity());
            nonAlcoholic.setMeasurementUnit(nai.measurementUnit());

            cocktailNonAlcoholicIngredientRepository.save(nonAlcoholic);
        }

        //adding taste profiles
        for(FlavourProfile flavour : cocktailDTO.flavourProfiles()) {
            CocktailFlavourProfile cfp = new CocktailFlavourProfile();
            cfp.setCocktail(savedCocktail);
            cfp.setFlavourProfile(flavour);

            cocktailFlavourProfileRepository.save(cfp);
        }

        return createCocktailDTO(savedCocktail);
    }

    public CocktailDTO update(Long id, CocktailDTO cocktailDTO) {
        Cocktail cocktail = cocktailRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Coctail with id: "+id+" not found"));

        cocktail.setName(cocktailDTO.name());
        cocktail.setPreparationMethod(cocktailDTO.preparationMethod());
        cocktail.setRecipe(cocktailDTO.recipe());

        cocktailAlcoholRepository.deleteByCocktail(cocktail);
        cocktailNonAlcoholicIngredientRepository.deleteByCocktail(cocktail);
        cocktailFlavourProfileRepository.deleteByCocktail(cocktail);

        //adding alcohols to coctail
        for(AlcoholInCocktailDTO aic : cocktailDTO.alcoholIngredients()) {
            CocktailAlcohol cocktailAlcohol = new CocktailAlcohol();
            cocktailAlcohol.setCocktail(cocktail);
            cocktailAlcohol.setAlcohol(alcoholRepository.findById(aic.alcohol().getId())
                    .orElseThrow(() ->new EntityNotFoundException("Alcohol with id: "+ aic.alcohol().getId() + " not found")));
            cocktailAlcohol.setQuantity(aic.quantity());
            cocktailAlcohol.setMeasurementUnit(aic.measurementUnit());

            cocktailAlcoholRepository.save(cocktailAlcohol);
        }

        //adding non-alcoholic ingredients to cocktail
        for(NonAlcoholicIngredientInCocktailDTO nai : cocktailDTO.nonAlcoholicIngredients()) {
            CocktailNonAlcoholicIngredient nonAlcoholic = new CocktailNonAlcoholicIngredient();
            nonAlcoholic.setCocktail(cocktail);
            nonAlcoholic.setNonAlcoholicIngredient(nonAlcoholicIngredientRepository.findById(nai.nonAlcoholicIngredient().getId())
                    .orElseThrow(() ->new EntityNotFoundException("non-alcoholic ingredient with id: "+ nai.nonAlcoholicIngredient().getId() + " not found")));
            nonAlcoholic.setQuantity(nai.quantity());
            nonAlcoholic.setMeasurementUnit(nai.measurementUnit());

            cocktailNonAlcoholicIngredientRepository.save(nonAlcoholic);
        }

        //adding taste profiles
        for(FlavourProfile flavour : cocktailDTO.flavourProfiles()) {
            CocktailFlavourProfile cfp = new CocktailFlavourProfile();
            cfp.setCocktail(cocktail);
            cfp.setFlavourProfile(flavour);

            cocktailFlavourProfileRepository.save(cfp);
        }

        cocktailRepository.save(cocktail);
        return createCocktailDTO(cocktail);
    }

    public void delete(Long id) {
        Cocktail cocktail = cocktailRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cocktail with id: " + id + " not found"));

        cocktailAlcoholRepository.deleteByCocktail(cocktail);
        cocktailNonAlcoholicIngredientRepository.deleteByCocktail(cocktail);
        cocktailFlavourProfileRepository.deleteByCocktail(cocktail);

        cocktailRepository.delete(cocktail);
    }


    private CocktailDTO createCocktailDTO(Cocktail cocktail) {

        List<AlcoholInCocktailDTO> alcohols = cocktailAlcoholRepository.findByCocktail(cocktail)
                .stream()
                .map(ca -> new AlcoholInCocktailDTO(ca.getAlcohol(), ca.getQuantity(), ca.getMeasurementUnit()))
                .collect(Collectors.toList());

        List<NonAlcoholicIngredientInCocktailDTO> nonAlcoholicIngredients = cocktailNonAlcoholicIngredientRepository.findByCocktail(cocktail)
                .stream()
                .map(cna -> new NonAlcoholicIngredientInCocktailDTO(cna.getNonAlcoholicIngredient(), cna.getQuantity(), cna.getMeasurementUnit()))
                .collect(Collectors.toList());

        List<FlavourProfile> flavourProfiles = cocktailFlavourProfileRepository.findByCocktail(cocktail)
                .stream()
                .map(CocktailFlavourProfile::getFlavourProfile)
                .collect(Collectors.toList());

        return new CocktailDTO(cocktail.getId(), cocktail.getName(), cocktail.getPreparationMethod(), cocktail.getRecipe(),
                alcohols, nonAlcoholicIngredients, flavourProfiles);
    }

}
