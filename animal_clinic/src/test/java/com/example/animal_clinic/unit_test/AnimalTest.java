package com.example.animal_clinic.unit_test;

import com.example.animal_clinic.VisitReason;
import com.example.animal_clinic.entity.AnimalEntity;
import com.example.animal_clinic.repository.AnimalRepository;
import com.example.animal_clinic.service.AnimalService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class AnimalTest {

    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private AnimalService animalService;

    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAnimalServiceGetAllMethod_shouldReturn_animalList(){
        List<AnimalEntity> animalEntityList = new ArrayList<>();
        animalEntityList.add(1L,"Bobik", "Laika",, VisitReason.CHECKUP)
    }

}
