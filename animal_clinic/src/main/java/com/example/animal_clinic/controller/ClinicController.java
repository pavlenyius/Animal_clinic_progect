package com.example.animal_clinic.controller;

import com.example.animal_clinic.service.ClinicService;
import com.example.animal_clinic.entity.ClinicEntity;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/api/clinic")
public class ClinicController{

    private final ClinicService clinicService;

    public ClinicController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @GetMapping
    public ResponseEntity<List<ClinicEntity>> getAll() {
        List<ClinicEntity> clinic = clinicService.getAll();
        return new ResponseEntity<>(clinic, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClinicEntity> getById(@PathVariable Long id) {

            ClinicEntity clinic = clinicService.getById(id);
            return new ResponseEntity<>(clinic, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<ClinicEntity> addNew(@RequestBody @Valid ClinicEntity clinic) {
        clinicService.addNew(clinic);
        return new ResponseEntity<>(clinic, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ClinicEntity> update(@PathVariable Long id, @RequestBody @Valid ClinicEntity update) {

            clinicService.update(id,update);
            return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

            clinicService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
