package com.example.Bartendly.model.service;

import com.example.Bartendly.model.Alcohol;
import com.example.Bartendly.model.DTO.AlcoholDTO;
import com.example.Bartendly.model.repository.AlcoholRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
public class AlcoholService {

    private final AlcoholRepository alcoholRepository;

    public List<AlcoholDTO> findAll() {
        return alcoholRepository.findAll().stream()
                .map(this::createAlcoholDTO)
                .collect(Collectors.toList());
    }

    public AlcoholDTO findById(Long id) {
        Alcohol alcohol = alcoholRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Alcohol with id: " + id + " not found"));

        return createAlcoholDTO(alcohol);
    }

    public AlcoholDTO create(AlcoholDTO alcoholDTO) {
        Alcohol alcohol = new Alcohol();
        alcohol.setName(alcoholDTO.name());
        alcohol.setType(alcoholDTO.type());

        Alcohol savedAlcohol = alcoholRepository.save(alcohol);

        return createAlcoholDTO(savedAlcohol);
    }

    public AlcoholDTO update(Long id, AlcoholDTO alcoholDTO) {
        Alcohol alcohol = alcoholRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Alcohol with id: " + id + " not found"));

        alcohol.setName(alcoholDTO.name());
        alcohol.setType(alcoholDTO.type());

        Alcohol updatedAlcohol = alcoholRepository.save(alcohol);

        return createAlcoholDTO(updatedAlcohol);
    }

    public void delete(Long id) {
        Alcohol alcohol = alcoholRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Alcohol with id: " + id + " not found"));

        alcoholRepository.delete(alcohol);
    }


    private AlcoholDTO createAlcoholDTO(Alcohol alcohol) {
        return new AlcoholDTO(alcohol.getId(), alcohol.getName(), alcohol.getType());
    }

}
