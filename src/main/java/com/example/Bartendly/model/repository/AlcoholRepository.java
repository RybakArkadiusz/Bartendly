package com.example.Bartendly.model.repository;

import com.example.Bartendly.model.Alcohol;
import com.example.Bartendly.model.Cocktail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlcoholRepository extends JpaRepository<Alcohol, Long> {

}
