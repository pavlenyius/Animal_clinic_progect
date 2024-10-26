package com.example.animal_clinic.controller;


import com.example.animal_clinic.service.ClientServise;
import com.example.animal_clinic.entity.ClientEntity;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/client")
public class ClientController {
    private final ClientServise clientServise;

    public ClientController(ClientServise clientServise) {
        this.clientServise = clientServise;
    }

    @GetMapping
    public ResponseEntity<List<ClientEntity>> getAll() {
        List<ClientEntity> client = clientServise.getAll();
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

   @GetMapping("/{id}")
    public ResponseEntity<ClientEntity> getById(@PathVariable Long id) {
        try {
            ClientEntity client = clientServise.getById(id);
            return new ResponseEntity<>(client, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional(timeout = 10,rollbackFor = Exception.class)
    @PostMapping
    public ResponseEntity<ClientEntity> addNew(@RequestBody @Valid ClientEntity client) {
        clientServise.addNew(client);
        return new ResponseEntity<>(client, HttpStatus.CREATED);

    }

    @Transactional(timeout = 10,rollbackFor = Exception.class)
    @PutMapping("/{id}")
    public ResponseEntity<ClientEntity> update(@PathVariable Long id, @RequestBody @Valid ClientEntity update) {
        try {
           clientServise.update(id, update);
            return new ResponseEntity<>(update, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional(timeout = 10,rollbackFor = Exception.class)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            clientServise.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
