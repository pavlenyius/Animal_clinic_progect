package com.example.animal_clinic.integrtionTest;


import com.example.animal_clinic.entity.ClientEntity;
import com.example.animal_clinic.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerIntegationTest {
    @Autowired
    private MockMvc mockMvc; // инжектим мок интерфейс

    @Autowired
    private ClientRepository clientRepository;

    @BeforeEach
    public void init() {
        clientRepository.deleteAll();
    }


    @Test
    public void testGetAllClients() throws Exception {
        ClientEntity client = new ClientEntity(null, "John", "test@example.com", "01234567890");
        clientRepository.save(client);

        mockMvc.perform(get("/api/client"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("John"));
    }

    @Test
    public void testGetClientByIdExist() throws Exception {
        ClientEntity client = new ClientEntity(null, "John", "test@example.com", "01234567890");
        clientRepository.save(client);

        mockMvc.perform(get("/api/client/" + client.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    public void testGetClientById_NotExist() throws Exception {
        mockMvc.perform(get("/api/client/100"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddNewClient() throws Exception {
        String JsonClient = "{\"name\":\"John\",\"email\":\"test@example.com\",\"phoneNumber\":\"01234567890\"}";

        mockMvc.perform(post("/api/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonClient))
                .andExpect(status().isCreated());

        List<ClientEntity> client = clientRepository.findAll();
        assertEquals(1, client.size());
    }

    @Test
    public void testUpgradeNewClientExist() throws Exception {

        ClientEntity client = new ClientEntity(null, "John", "test@example.com", "01234567890");
        clientRepository.save(client);
        String updateJson = "{\"name\":\"Adam\",\"email\":\"adam@example.com\",\"phoneNumber\":\"09876543210\"}";

        mockMvc.perform(put("/api/client/" + client.getId()).
                        contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk());

        ClientEntity clientEntity = clientRepository.findById(client.getId()).orElseThrow();
        assertEquals("Adam", clientEntity.getName());
    }

    @Test
    public void teatUpgrdeNewClientifNotExist() throws Exception {
        String updateJson = "{\"name\":\"Adam\",\"email\":\"adam@example.com\",\"phoneNumber\":\"09876543210\"}";

        mockMvc.perform(put("/api/client/100")
                        .contentType(MediaType.APPLICATION_JSON).
                        content(updateJson))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteClientifExist() throws Exception {
        ClientEntity client = new ClientEntity(null, "John", "test@example.com", "01234567890");
        clientRepository.save(client);

        mockMvc.perform(delete("/api/client/"+client.getId()))
                .andExpect(status().isNoContent());

        assertTrue(clientRepository.findById(client.getId()).isEmpty());

    }

    @Test
    public void testDeleteClientIfNotExist() throws Exception{
        mockMvc.perform(delete("/api/client/100"))
                .andExpect(status().isNotFound());
    }


}

