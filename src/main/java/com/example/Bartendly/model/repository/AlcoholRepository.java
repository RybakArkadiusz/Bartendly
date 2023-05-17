package com.example.Bartendly.model.repository;

import com.example.Bartendly.model.Alcohol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlcoholRepository extends JpaRepository<Alcohol, Long> {
}
