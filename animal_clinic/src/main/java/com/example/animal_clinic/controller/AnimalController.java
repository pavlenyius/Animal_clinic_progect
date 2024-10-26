package com.example.animal_clinic.controller;

import com.example.animal_clinic.service.AnimalService;
import com.example.animal_clinic.VisitReason;
import com.example.animal_clinic.entity.AnimalEntity;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/api/animals")
public class AnimalController {
    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }


    @GetMapping
    public ResponseEntity<List<AnimalEntity>> getAllAnimals() {
        List<AnimalEntity> animal = animalService.getAll();
        return new ResponseEntity<>(animal, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalEntity> getAnimalById(@PathVariable Long id) {
        try {
            AnimalEntity animal = animalService.getById(id);
            return new ResponseEntity<>(animal, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<List<AnimalEntity>> getAnimalByFilter(
            @RequestParam(required = false) Long clientId,
            @RequestParam(required = false) LocalDate visitDate,
            @RequestParam(required = false) VisitReason visitReason,
            @RequestParam(required = false) String animalBreed
    ) {
        List<AnimalEntity> animalEntities =
                animalService.findAnimalByFilter(clientId, visitDate, visitReason, animalBreed);

        if (animalEntities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
            return new ResponseEntity<>(animalEntities, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AnimalEntity> addNewAnimal(@RequestBody @Valid AnimalEntity animal) {
        animalService.addNew(animal);
        return new ResponseEntity<>(animal, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimalEntity> updateAnimal(@PathVariable Long id, @RequestBody @Valid AnimalEntity update) {
        try {
           animalService.update(id,update);
            return new ResponseEntity<>(update, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long id) {
        try {
            animalService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
