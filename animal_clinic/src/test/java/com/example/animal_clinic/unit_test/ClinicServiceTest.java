package com.example.animal_clinic.unit_test;

import com.example.animal_clinic.entity.ClinicEntity;
import com.example.animal_clinic.repository.ClinicRepository;
import com.example.animal_clinic.service.ClinicService;
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

public class ClinicServiceTest {

    @Mock
    private ClinicRepository clinicRepository;

    @InjectMocks
    private ClinicService clinicService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllClinic() {
        List<ClinicEntity> clinicEntityList = new ArrayList<>();
        clinicEntityList.add(new ClinicEntity(1L, "Ul Lenina"));
        clinicEntityList.add(new ClinicEntity(2L, "Nevskiy prosprct"));

        when(clinicRepository.findAll()).thenReturn(clinicEntityList);

        List<ClinicEntity> clinicEntities = clinicService.getAll();

        assertNotNull(clinicEntities);
        assertEquals(2, clinicEntities.size());
        assertEquals("Ul Lenina", clinicEntities.get(0).getAddress());
    }

    @Test
    public void testGetClinicByIdIfExist_ReturnClinic() {
        Long clinicId = 1L;

        ClinicEntity mockClinic = new ClinicEntity(clinicId, "Ul Lenina");

        when(clinicRepository.findById(clinicId)).thenReturn(Optional.of(mockClinic));

        ClinicEntity clinic = clinicService.getById(clinicId);

        assertNotNull(clinic);
        assertEquals(clinicId, clinic.getId());
        assertEquals("Ul Lenina", clinic.getAddress());
    }

    @Test
    public void testGetClinicByIdIfNotExist_returnExeption() {
        Long clinicId = 1L;
        when(clinicRepository.findById(clinicId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> clinicService.getById(clinicId));
    }

    @Test
    public void testAddNewClinic() {
        Long clinicId = 1L;

        ClinicEntity mockClinic = new ClinicEntity(clinicId, "Ul Lenina");

        when(clinicRepository.save(any(ClinicEntity.class))).thenReturn(mockClinic);

        ClinicEntity clinic = clinicService.addNew(mockClinic);

        assertNotNull(clinicId);
        assertEquals(clinicId, clinic.getId());
        assertEquals("Ul Lenina", clinic.getAddress());

    }

    @Test
    public void testUpdateClinicIfExist_ReturnClinic() {

        Long clinicId = 1L;

        ClinicEntity mockClinic = new ClinicEntity(clinicId, "Ul Lenina");
        ClinicEntity updateClinic = new ClinicEntity(null, "Nevskiy pr");

        when(clinicRepository.findById(clinicId)).thenReturn(Optional.of(mockClinic));
        when(clinicRepository.save(any(ClinicEntity.class))).thenReturn(updateClinic);

        ClinicEntity clinic = clinicService.update(clinicId, updateClinic);

        assertNotNull(clinicId);
        assertEquals("Nevskiy pr", updateClinic.getAddress());
    }

    @Test
    public void testUpdateClinicIfNotExist_ReturnException() {

        Long notExistId = 1L;
        ClinicEntity clinic = new ClinicEntity(null, "Lenina st");

        when(clinicRepository.findById(notExistId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,()->clinicService.update(notExistId,clinic));

    }

    @Test
    public void testDeleteByIdIfExist_ReturnClinic() {
        Long clinicId = 1L;

        when(clinicRepository.existsById(clinicId)).thenReturn(true);

        clinicService.delete(clinicId);

        verify(clinicRepository, times(1)).deleteById(clinicId);

    }

    @Test
    public void testDeleteByIdIfNotExist_ResultException() {
        Long clinicId = 1L;

        when(clinicRepository.existsById(clinicId)).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> clinicService.delete(clinicId));
    }

}
