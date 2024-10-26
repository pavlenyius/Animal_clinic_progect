package com.example.animal_clinic.unit_test;


import com.example.animal_clinic.entity.ClientEntity;
import com.example.animal_clinic.repository.ClientRepository;
import com.example.animal_clinic.service.ClientServise;
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


public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository; // создаем мок для ClientRepository

    @InjectMocks
    private ClientServise clientServise; // что тестим

    @BeforeEach // выполняем перед каждый раз
    public void setUp() {
        MockitoAnnotations.openMocks(this); // каждый раз создаем моковые записи
    }

    @Test
    public void testGetAllClients_sucsessfullResult() {
        //Arrange условия
        List<ClientEntity> mockList = new ArrayList<>(); // создали мок объекты
        mockList.add(new ClientEntity(1L, "John", "dfdff@example.ru", "01234567890"));
        mockList.add(new ClientEntity(2L, "Anna", "77d7f@example.ru", "09876543210"));

        when(clientRepository.findAll()).thenReturn(mockList); // имитируем вызов команды репозитория

        //Act фактическое выполнение тестируемого действия
        List<ClientEntity> clients = clientServise.getAll(); // проверяем действительно ли нам файнд олл все возвращает


        //Assert проверка результатов выполнения.
        assertNotNull(clients); //проверяем что не нулл
        assertEquals(2, clients.size()); // сравниваем размер списка
        assertEquals("John", clients.get(0).getName()); // сравниваем имя первого клиента с его параметрами

    }

    @Test
    public void testGetClientByIdIfExist_ResultClient() {
        Long clientId = 1L;

        ClientEntity client = new ClientEntity(clientId, "John", "dfdff@example.ru", "01234567890");

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client)); // обязательно возвращаем моковое значение АйДи

        //Act
        ClientEntity clientEntity = clientServise.getById(clientId);

        //Assert
        assertNotNull(clientEntity); // client not null
        assertEquals(clientId, clientEntity.getId()); // метод репозитория равен методу сервиса
        assertEquals("John", clientEntity.getName());


    }

    @Test

    public void testGetClientByIdIfExist_ResultExeption() {
        Long clientId = 1L;
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        //Act and Assert

        assertThrows(NoSuchElementException.class, () -> clientServise.getById(clientId));

    }

    @Test
    public void testAddClient() {

        Long clientId = 1L;
        ClientEntity mockClient = new ClientEntity(clientId, "John", "john@example.com", "01234567890");

        // Настраиваем мок-репозиторий, чтобы он возвращал mockClient при вызове save
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(mockClient);

        // Act
        ClientEntity addedClient = clientServise.addNew(mockClient); // Передаем mockClient в метод addNew

        // Assert
        assertNotNull(addedClient); // Проверяем, что возвращенный объект не null
        assertEquals(clientId, addedClient.getId()); // Проверяем, что ID совпадает
        assertEquals("John", addedClient.getName()); // Проверяем, что имя совпадает

    }

    @Test
    public void updateClientExist() {
        Long clientId = 1L;

        ClientEntity mockClient = new ClientEntity(clientId, "John", "dfdff@example.ru", "01234567890");
        ClientEntity uprade = new ClientEntity(null, "Adam", "wqwwwwwwwwwf@example.ru", "01234567890");

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(mockClient));

        when(clientRepository.save(any(ClientEntity.class))).thenReturn(uprade);
        //Act
        ClientEntity updateClient = clientServise.update(clientId, uprade);

        //assert
        assertNotNull(updateClient);
        assertEquals("Adam", updateClient.getName());
        assertEquals("wqwwwwwwwwwf@example.ru", updateClient.getEmail());

    }

    @Test
    public void updateClientIfNotExist() {
        Long notExistID = 1L;
        ClientEntity client = new ClientEntity(null, "Ivan", "ddd@example.com", "01234567890");

        when(clientRepository.findById(notExistID)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> clientServise.update(notExistID, client));
    }


    @Test
    public void testDeleteIfExist() {
        Long clientId = 1L;
        when(clientRepository.existsById(clientId)).thenReturn(true);

        clientServise.delete(clientId);
        // Assert
        verify(clientRepository, times(1)).deleteById(clientId); // проверяем, что метод deleteById был вызван один раз

    }

    @Test
    public void testDeleteIfNotExist() {
        Long clientId = 1L;

        when(clientRepository.existsById(clientId)).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> clientServise.delete(clientId));
    }
}
