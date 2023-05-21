package com.example.Bartendly.model.controller;

import com.example.Bartendly.model.DTO.AlcoholDTO;
import com.example.Bartendly.model.service.AlcoholService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alcohol")
@AllArgsConstructor
public class AlcoholController {

    private final AlcoholService alcoholService;


    @GetMapping//working
    public List<AlcoholDTO> findAll() {
        return alcoholService.findAll();
    }

    @GetMapping("/{id}")//working
    public AlcoholDTO findById(@PathVariable Long id) {
        return alcoholService.findById(id);
    }

    @PostMapping//working
    public ResponseEntity<AlcoholDTO> create(@RequestBody AlcoholDTO alcoholDTO) {
        return new ResponseEntity<>(alcoholService.create(alcoholDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")//working
    public AlcoholDTO update(@PathVariable Long id, @RequestBody AlcoholDTO alcoholDTO) {
        return alcoholService.update(id, alcoholDTO);
    }

    @DeleteMapping("/{id}")//working
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alcoholService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
