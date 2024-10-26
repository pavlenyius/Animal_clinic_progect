package com.example.animal_clinic.service;

import com.example.animal_clinic.entity.AnimalEntity;
import com.example.animal_clinic.repository.ClientRepository;
import com.example.animal_clinic.entity.ClientEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ClientServise implements BaseService<ClientEntity,Long> {
    private final ClientRepository clientRepository;

    public ClientServise(ClientRepository clientRepository)
    {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<ClientEntity> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public ClientEntity getById(Long id) {
        Optional<ClientEntity> clientEntity = clientRepository.findById(id);
        return clientEntity.orElseThrow(()->new NoSuchElementException("No such id: " + id));
    }

    public ClientEntity addNew (ClientEntity clientEntity) {
        return clientRepository.save(clientEntity);
    }

    @Override
    public ClientEntity update(Long id, ClientEntity upgradeClient) {
        Optional<ClientEntity> clientEntity = clientRepository.findById(id);
        if (clientEntity.isPresent()) {
            ClientEntity client = clientEntity.get();
            client.setName(upgradeClient.getName());
            client.setEmail(upgradeClient.getEmail());
            client.setPhoneNumber(upgradeClient.getPhoneNumber());

            return
            clientRepository.save(client);
        } else throw new NoSuchElementException("No such client");
    }

    @Override
    public void delete(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
        } else throw new NoSuchElementException("No such client");
    }
}