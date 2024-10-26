package com.example.animal_clinic.unit_test;

import com.example.animal_clinic.entity.ClientCardEntity;
import com.example.animal_clinic.repository.ClientCardRepository;
import com.example.animal_clinic.service.ClientCardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientCardTest {

    @Mock
    private ClientCardRepository clientCardRepository;

    @InjectMocks

    private ClientCardService clientCardService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll(){
        List<ClientCardEntity> mockList = new ArrayList<>();
        mockList.add(new ClientCardEntity(1L,"Good"));
        mockList.add(new ClientCardEntity(2L,"Bad"));
        when(clientCardRepository.findAll()).thenReturn(mockList);

       List<ClientCardEntity> clientCardEntities = clientCardService.getAll();

       assertNotNull(clientCardEntities);
       assertEquals(2,clientCardEntities.size());
       assertEquals("Good",clientCardEntities.get(0).getClientInfo());
    }

    @Test
    public void testGetByIdIfExist_ResultCard(){
        Long clientId = 1L;

        ClientCardEntity mockClientCard = new ClientCardEntity(clientId,"Good");

        when(clientCardRepository.findById(clientId)).thenReturn(Optional.of(mockClientCard));

        ClientCardEntity card = clientCardService.getById(clientId);

        assertNotNull(card);
        assertEquals(clientId,card.getId());
        assertEquals("Good",card.getClientInfo());
    }

    @Test
    public void testGetByIdIfNoExist_ResulException(){
        Long clientId= 1L;

        when(clientCardRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,()->clientCardService.getById(clientId));
    }

    @Test
    public void testAddClientCard(){
        Long clientId = 1L;

        ClientCardEntity mockClientCard = new ClientCardEntity(clientId,"Good");

        when(clientCardRepository.save(mockClientCard)).thenReturn(mockClientCard);

        ClientCardEntity card = clientCardService.addNew(mockClientCard);

        assertNotNull(card);
        assertEquals(clientId,card.getId());
        assertEquals("Good",card.getClientInfo());
    }

    @Test
    public void testUpdateClientCardIfExist_resultClientCard(){
        Long clientId=1L;
        ClientCardEntity old = new ClientCardEntity(clientId,"One");
        ClientCardEntity update= new ClientCardEntity(null,"two");
        when(clientCardRepository.findById(clientId)).thenReturn(Optional.of(old));
        when(clientCardRepository.save(any(ClientCardEntity.class))).thenReturn(update);

        ClientCardEntity card = clientCardService.update(clientId,update);

        assertNotNull(card);
        assertEquals("two",update.getClientInfo());
    }

    @Test
    public  void testUpdateClientCardIfNotExist(){
        Long clientId=1L;
        ClientCardEntity clientCard= new ClientCardEntity(clientId,"One");

        when(clientCardRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,()->clientCardService.update(clientId,clientCard));
    }

    @Test
    public void testDeleteClientCardIfExist(){
        Long clientCard = 1L;

        when(clientCardRepository.existsById(clientCard)).thenReturn(true);

        clientCardService.delete(clientCard);
        verify(clientCardRepository,times(1)).deleteById(clientCard);
    }

    @Test
    public void testDeleteIfNotExist(){
        Long clienId=1L;
        when(clientCardRepository.existsById(clienId)).thenReturn(false);

        assertThrows(NoSuchElementException.class,()->clientCardService.delete(clienId));
    }
}
