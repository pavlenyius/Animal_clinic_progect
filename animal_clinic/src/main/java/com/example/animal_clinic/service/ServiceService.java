package com.example.animal_clinic.service;

import com.example.animal_clinic.repository.ServiceRepository;
import com.example.animal_clinic.entity.ServiceEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ServiceService implements BaseService<ServiceEntity, Long> {
    private final ServiceRepository serviceRepository;
    private final PlatformTransactionManager platformTransactionManager;

    public ServiceService(ServiceRepository serviceRepository, PlatformTransactionManager platformTransactionManager) {
        this.serviceRepository = serviceRepository;
        this.platformTransactionManager = platformTransactionManager;
    }

    @Override
    public List<ServiceEntity> getAll() {
        return serviceRepository.findAll();
    }

    @Override
    public ServiceEntity getById(Long id) {
        Optional<ServiceEntity> serviceEntity = serviceRepository.findById(id);
        return serviceEntity.orElseThrow(() -> new NoSuchElementException("No such id " + id));
    }

    @Override
    public ServiceEntity addNew(ServiceEntity serviceEntity) {
        TransactionStatus status = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            ServiceEntity savedEntity = serviceRepository.save(serviceEntity);
            platformTransactionManager.commit(status); // Завершение транзакции
            return savedEntity;
        } catch (Exception e) {
            platformTransactionManager.rollback(status);
            throw e;
        }
    }

    @Override
    public ServiceEntity update(Long id, ServiceEntity update) {
        TransactionStatus status = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            Optional<ServiceEntity> serviceEntity = serviceRepository.findById(id);
            if (serviceEntity.isPresent()) {
                ServiceEntity service = serviceEntity.get();
                service.setTypeOfSrevice(update.getTypeOfSrevice());
                ServiceEntity updatedEntity = serviceRepository.save(service);
                platformTransactionManager.commit(status); // Завершение транзакции
                return updatedEntity;
            } else {
                throw new NoSuchElementException("No such element");
            }
        } catch (Exception e) {
            platformTransactionManager.rollback(status); // Откат транзакции в случае ошибки
            throw e; // Повторное выбрасывание исключения для обработки выше
        }
    }

    @Override
    public void delete(Long id) {
        TransactionStatus status = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            if (serviceRepository.existsById(id)) {
                serviceRepository.deleteById(id);
                platformTransactionManager.commit(status);
            } else {
                platformTransactionManager.rollback(status);
                throw new NoSuchElementException("No such element");
            }
        } catch (Exception e) {
            platformTransactionManager.rollback(status);
            throw e;
        }
    }
}
