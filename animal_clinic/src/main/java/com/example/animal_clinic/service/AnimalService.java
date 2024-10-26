package com.example.animal_clinic.service;

import com.example.animal_clinic.repository.AnimalRepository;
import com.example.animal_clinic.repository.ClientRepository;
import com.example.animal_clinic.VisitReason;
import com.example.animal_clinic.entity.AnimalEntity;
import com.example.animal_clinic.entity.ClientEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AnimalService implements BaseService<AnimalEntity,Long>{
    private final AnimalRepository animalRepository;
    private final ClientServise clientServise;

    public AnimalService(AnimalRepository animalRepository, ClientRepository clientRepository, ClientServise clientServise) {
        this.animalRepository = animalRepository;
        this.clientServise = clientServise;
    }

    public ClientEntity findClientById(Long clientId) {
        return clientServise.getById(clientId);
    }

    @Override
    public List<AnimalEntity> getAll() {
        return animalRepository.findAll();
    }

    @Override
    public AnimalEntity getById(Long id) {
        Optional<AnimalEntity> animalEntity = animalRepository.findById(id);
        return animalEntity.orElseThrow(()-> new NoSuchElementException("No such"+id));
    }


    @Override
    public AnimalEntity addNew(AnimalEntity animalEntity) {

        return animalRepository.save(animalEntity);
    }

    @Override
    public AnimalEntity update(Long id, AnimalEntity upgradeAnimal) {
        Optional<AnimalEntity> animalEntity = animalRepository.findById(id);
        if (animalEntity.isPresent()) {
            AnimalEntity animal = animalEntity.get();
            animal.setAnimalName(upgradeAnimal.getAnimalName());
            animal.setAnimalBreed(upgradeAnimal.getAnimalBreed());
            animal.setVisitDate(upgradeAnimal.getVisitDate());
            animal.setVisitReason(upgradeAnimal.getVisitReason());
           return animalRepository.save(animal);
        } else throw new NoSuchElementException("No such client");
    }
@Override
    public void delete(Long id) {
        if (animalRepository.existsById(id)) {
            animalRepository.deleteById(id);
        } else throw new NoSuchElementException("no such client");
    }

    public List<AnimalEntity> findAnimalByFilter
            (Long clientId, LocalDate visitDate, VisitReason visitReason, String animalBreed) {
        ClientEntity client = null;

        if (clientId != null) {
            client = clientServise.getById(clientId);

        }
        return animalRepository.findAnimalByFilter(client, visitDate, visitReason, animalBreed);
    }
}


