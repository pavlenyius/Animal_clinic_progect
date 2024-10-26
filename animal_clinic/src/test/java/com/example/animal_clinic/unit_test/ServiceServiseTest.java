package com.example.animal_clinic.unit_test;

import com.example.animal_clinic.entity.ServiceEntity;
import com.example.animal_clinic.repository.ServiceRepository;
import com.example.animal_clinic.service.ServiceService;
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

public class ServiceServiseTest {

    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private ServiceService serviceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllServices() {
        List<ServiceEntity> serviceEntities = new ArrayList<>();
        serviceEntities.add(new ServiceEntity(1L, "Check up"));
        serviceEntities.add(new ServiceEntity(2L, "Bind"));

        when(serviceRepository.findAll()).thenReturn(serviceEntities);

        List<ServiceEntity> service = serviceService.getAll();

        assertNotNull(service);
        assertEquals(2,serviceEntities.size());
        assertEquals("Check up",service.get(0).getTypeOfSrevice());
    }

    @Test
    public void testFindBiIdIfExist_ReturnService(){

        Long serviceId = 1L;
        ServiceEntity mockService= new ServiceEntity(serviceId,"Check up");

        when(serviceRepository.findById(serviceId)).thenReturn(Optional.of(mockService));

        ServiceEntity service= serviceService.getById(serviceId);
        assertNotNull(service);
        assertEquals(serviceId,service.getId());
        assertEquals("Check up",service.getTypeOfSrevice());
    }

    @Test
    public void testFindByIdIfNotExist_ResultException(){

        Long serviceId=1L;
        when(serviceRepository.findById(serviceId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,()->serviceService.getById(serviceId));
    }

    @Test
    public void testAddNew(){
        Long serviceId=1L;
        ServiceEntity service = new ServiceEntity(serviceId,"Check up");

        when(serviceRepository.save(any(ServiceEntity.class))).thenReturn(service);

        ServiceEntity serviceEntity = serviceService.addNew(service);

        assertNotNull(serviceEntity);
        assertEquals(serviceId,serviceEntity.getId());
        assertEquals("Check up",serviceEntity.getTypeOfSrevice());
    }

    @Test
    public void testUpgradeIfExist(){
        Long serviceId=1L;

        ServiceEntity befourUpgrade = new ServiceEntity(serviceId,"Check up");
        ServiceEntity updated = new ServiceEntity(null, "Pay Money");

        when(serviceRepository.findById(serviceId)).thenReturn(Optional.of(befourUpgrade));

        when(serviceRepository.save(any(ServiceEntity.class))).thenReturn(updated);

        ServiceEntity service = serviceService.update(serviceId,updated);

        assertNotNull(service);
        assertEquals("Pay Money",service.getTypeOfSrevice());
    }

    @Test
    public void testUpgradeIfNotExist_resultException(){
        Long serviceId =1L;
        when(serviceRepository.findById(serviceId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,()->serviceService.delete(serviceId));
    }

    @Test
    public void deleteIfExist(){
        Long serviceId=1L;
        when(serviceRepository.existsById(serviceId)).thenReturn(true);
        serviceService.delete(serviceId);

        verify(serviceRepository,times(1)).deleteById(serviceId);
    }
    @Test
    public void deliteIfnotExist(){
        Long serviceId=1L;
        when(serviceRepository.existsById(serviceId)).thenReturn(false);

        assertThrows(NoSuchElementException.class,()-> serviceService.delete(serviceId));
    }

}
