package com.example.animal_clinic.controller;


import com.example.animal_clinic.service.ServiceService;
import com.example.animal_clinic.entity.ServiceEntity;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/service")
public class ServiceController {

private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }
    
    

    @GetMapping
    public ResponseEntity<List<ServiceEntity>> getAllService() {
        List<ServiceEntity> client = serviceService.getAll();
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceEntity> getServiceById(@PathVariable Long id) {
        try {
            ServiceEntity service = serviceService.getById(id);
            return new ResponseEntity<>(service, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ServiceEntity> addNewService(@RequestBody @Valid ServiceEntity service) {
        serviceService.addNew(service);
        return new ResponseEntity<>(service, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceEntity> updateService(@PathVariable Long id, @RequestBody @Valid ServiceEntity update) {
        try {
            serviceService.update(id, update);
            return new ResponseEntity<>(update, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        try {
            serviceService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
