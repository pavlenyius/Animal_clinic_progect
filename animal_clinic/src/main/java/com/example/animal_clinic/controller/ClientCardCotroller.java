package com.example.animal_clinic.controller;

import com.example.animal_clinic.service.ClientCardService;
import com.example.animal_clinic.entity.ClientCardEntity;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/CardControllre")
public class ClientCardCotroller {
    private final ClientCardService clientCardCardService
;

    public ClientCardCotroller(ClientCardService clientCardCardService) {
        this.clientCardCardService = clientCardCardService;
    }


    @GetMapping
    public ResponseEntity<List<ClientCardEntity>> getAllClientCard() {
        List<ClientCardEntity
> clientCard = clientCardCardService
.getAll();
        return new ResponseEntity<>(clientCard, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientCardEntity
> getById(@PathVariable Long id) {
        try {
            ClientCardEntity
 clientCard = clientCardCardService.getById(id);
            return new ResponseEntity<>(clientCard, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ClientCardEntity> addNewClient(@RequestBody @Valid ClientCardEntity clientCard) {
        clientCardCardService.addNew(clientCard);
        return new ResponseEntity<>(clientCard, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientCardEntity
> updateClient(@PathVariable Long id, @RequestBody @Valid ClientCardEntity
 update) {
        try {
           clientCardCardService.update(id, update);

            return new ResponseEntity<>(update, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        try {
            clientCardCardService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
