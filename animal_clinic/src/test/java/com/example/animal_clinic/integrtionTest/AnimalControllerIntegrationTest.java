//package com.example.animal_clinic.integrtionTest;
//
//import com.example.animal_clinic.VisitReason;
//import com.example.animal_clinic.entity.AnimalEntity;
//import com.example.animal_clinic.entity.ClientEntity;
//import com.example.animal_clinic.repository.AnimalRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class AnimalControllerIntegrationTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private AnimalRepository animalRepository;
//
//    @BeforeEach
//    public void init() {
//        animalRepository.deleteAll();
//    }
//
//
//    @Test
//    public void testGetAll() throws Exception {
//        AnimalEntity animalEntity = new AnimalEntity(null, "Bobik", "Haski", LocalDate.of(2019, 01, 01), VisitReason.CHECKUP);
//
//        animalRepository.save(animalEntity);
//        animalRepository.findAnimalByFilter();
//
//        mockMvc.perform(get("/api/animals"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].animalName").value("Bobik"));
//    }
//
//    @Test
//    public void testGetById_ifExist() throws Exception {
//        AnimalEntity animalEntity = new AnimalEntity(null, "Bobik", "Haski", LocalDate.of(2019, 01, 01), VisitReason.CHECKUP);
//        animalRepository.save(animalEntity);
//
//        mockMvc.perform(get("/api/animals/" + animalEntity.getId()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.animalName").value("Bobik"));
//
//    }
//
//    @Test
//    public void testGetById_ifNotExist() throws Exception {
//        mockMvc.perform(get("/api/animals/100"))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void testAddNew() throws Exception {
//        String newEntity = "{\"animalName\":\"Bobik\",\"animalBreed\":\"Haski\",\"localDate\":\"2019-01-01\",\"visitReason\":\"CHECKUP\"}";
//
//        mockMvc.perform(post("/api/animals")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(newEntity))
//                .andExpect(status().isCreated());
//
//        List<AnimalEntity> animalEntities = animalRepository.findAll();
//
//        assertEquals(1, animalEntities.size());
//    }
//
//    @Test
//    public void updateIfExist() throws Exception {
//        AnimalEntity animalEntity = new AnimalEntity(null, "Bobik", "Haski", LocalDate.of(2019, 01, 01), VisitReason.CHECKUP);
//        animalRepository.save(animalEntity);
//
//        String upgrade = "{\"animalName\":\"Bobik1\",\"animalBreed\":\"Haski1\",\"localDate\":\"2015-01-01\",\"visitReason\":\"CHECKUP\"}";
//
//        mockMvc.perform(put("/api/animals/" + animalEntity.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(upgrade))
//                .andExpect(status().isOk());
//
//        AnimalEntity animal = animalRepository.findById(animalEntity.getId()).orElseThrow();
//
//        assertEquals("Bobik1", animal.getAnimalName());
//
//    }
//
//    @Test
//    public void testAnimalByIdif_NotExist() throws Exception {
//        String upgrade = "{\"animalName\":\"Bobik1\",\"animalBreed\":\"Haski1\",\"localDate\":\"2015-01-01\",\"visitReason\":\"CHECKUP\"}";
//
//        mockMvc.perform(put("/api/animals/100")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(upgrade))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void testDeliteIfExist() throws Exception{
//        AnimalEntity animalEntity = new AnimalEntity(null, "Bobik", "Haski", LocalDate.of(2019, 01, 01), VisitReason.CHECKUP);
//        animalRepository.save(animalEntity);
//
//        mockMvc.perform(delete("/api/animals/"+animalEntity.getId()))
//                .andExpect(status().isNoContent());
//
//        assertTrue(animalRepository.findById(animalEntity.getId()).isEmpty());
//    }
//
//    @Test
//    public void testDeleteIfNotExist() throws Exception{
//        mockMvc.perform(delete("/api/animals//100"))
//                .andExpect(status().isNotFound());
//    }
//
//
//}
