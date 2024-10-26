package com.example.animal_clinic.service;

import com.example.animal_clinic.repository.ServiceRepository;
import com.example.animal_clinic.entity.ServiceEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ServiceService implements BaseService<ServiceEntity,Long>{
private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public List<ServiceEntity> getAll () {
        return serviceRepository.findAll();
    }

    @Override
    public ServiceEntity getById(Long id){
        Optional<ServiceEntity> serviceEntity = serviceRepository.findById(id);
        return serviceEntity.orElseThrow(()->new NoSuchElementException("No such id " +id));
    }

    @Override
    public ServiceEntity addNew (ServiceEntity serviceEntity){return serviceRepository.save(serviceEntity);
    }

    @Override
    public ServiceEntity update (Long id, ServiceEntity update){
        Optional<ServiceEntity> serviceEntity = serviceRepository.findById(id);

        if (serviceEntity.isPresent()){
           ServiceEntity service = serviceEntity.get();
           service.setTypeOfSrevice(update.getTypeOfSrevice());
           return
           serviceRepository.save(service);
        }
        else throw new NoSuchElementException("no such element");
    }

    @Override
    public void delete(Long id){
        if (serviceRepository.existsById(id)){
        serviceRepository.deleteById(id);}
        else throw new NoSuchElementException("No such element");
    }
}
