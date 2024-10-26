package com.example.animal_clinic.service;

import com.example.animal_clinic.repository.ClientCardRepository;
import com.example.animal_clinic.entity.ClientCardEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
@Service

public class ClientCardService implements BaseService<ClientCardEntity,Long> {
    
    private final ClientCardRepository clientCardRepository;

    public ClientCardService(ClientCardRepository clientCardRepository) {
        this.clientCardRepository = clientCardRepository;
        
    }

    @Override
    public List<ClientCardEntity> getAll () {
        return clientCardRepository.findAll();
    }

    @Override
    public ClientCardEntity getById(Long id){
        Optional<ClientCardEntity> clientCardEntity = clientCardRepository.findById(id);
        return clientCardEntity.orElseThrow(()-> new NoSuchElementException("No such id "+id));
    }

    @Override
    public ClientCardEntity addNew (ClientCardEntity clientCardEntity){
        return clientCardRepository.save(clientCardEntity);
    }

    @Override
    public ClientCardEntity update (Long id, ClientCardEntity update){
        Optional<ClientCardEntity> clientCardEntity= clientCardRepository.findById(id);

        if(clientCardEntity.isPresent()){
            ClientCardEntity clientCard = clientCardEntity.get();
            clientCard.setClientInfo(update.getClientInfo());
            return clientCardRepository.save(clientCard);
        }
        else throw new NoSuchElementException("No such element");
    }

    @Override
    public void delete(Long id){
        if (clientCardRepository.existsById(id)){
        clientCardRepository.deleteById(id);}
        else throw new NoSuchElementException("No such element");
    }
}
