package com.example.animal_clinic.entity;


import com.example.animal_clinic.VisitReason;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
//@NoArgsConstructor
public class AnimalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Please write the animal breed")
    private String animalName;

    @NotBlank(message = "Please write the animal breed")
    private String animalBreed;

    @PastOrPresent (message = "You cant set the date from future")
    private LocalDate visitDate;

    @Enumerated(EnumType.STRING)
    private VisitReason visitReason;

@ManyToOne
@JoinColumn(name = "client_id")
    ClientEntity clientEntity;

    public AnimalEntity(Long id, String animalName, String animalBreed, LocalDate visitDate, VisitReason visitReason) {
        this.id = id;
        this.animalName = animalName;
        this.animalBreed = animalBreed;
        this.visitDate = visitDate;
        this.visitReason = visitReason;
    }
}
