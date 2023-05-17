package com.example.Bartendly.model.db;

import com.example.Bartendly.model.Cocktail;
import com.example.Bartendly.model.FlavourProfile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CocktailFlavourProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cocktail_id")
    private Cocktail cocktail;
    @Enumerated(EnumType.STRING)
    private FlavourProfile flavourProfile;
}
