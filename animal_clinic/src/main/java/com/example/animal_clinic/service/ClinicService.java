package com.example.animal_clinic.service;

import com.example.animal_clinic.exception.ClinicNotFoundException;
import com.example.animal_clinic.exception.ValidationException;
import com.example.animal_clinic.repository.ClinicRepository;
import com.example.animal_clinic.entity.ClinicEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service

public class ClinicService implements BaseService<ClinicEntity, Long> {
    private final ClinicRepository clinicRepository;
    private final TransactionTemplate transactionTemplate;

    public ClinicService(ClinicRepository clinicRepository, TransactionTemplate transactionTemplate) {
        this.clinicRepository = clinicRepository;
        this.transactionTemplate = new TransactionTemplate(transactionTemplate.getTransactionManager());
    }

    @Override
    public List<ClinicEntity> getAll() {
        return clinicRepository.findAll();
    }

    @Override
    public ClinicEntity getById(Long id) {
        Optional<ClinicEntity> clinicEntity = clinicRepository.findById(id);
        return clinicEntity.orElseThrow(() -> new ClinicNotFoundException(id));
    }


    @Override
    public ClinicEntity addNew(ClinicEntity clinicEntity) {
        if (clinicEntity.getAddress() == null || clinicEntity.getAddress().isBlank()) {
            throw new ValidationException(" Write the address");
        }
        return transactionTemplate.execute(new TransactionCallback<ClinicEntity>() {
            @Override
            public ClinicEntity doInTransaction(TransactionStatus status) {
                return clinicRepository.save(clinicEntity);
            }
        });

    }

    @Override
    public ClinicEntity update(Long id, ClinicEntity update) {
        return transactionTemplate.execute(new TransactionCallback<ClinicEntity>() {
            @Override
            public ClinicEntity doInTransaction(TransactionStatus status) {
                Optional<ClinicEntity> clinicEntity = clinicRepository.findById(id);
                if (clinicEntity.isPresent()) {
                    ClinicEntity clinic = clinicEntity.get();
                    clinic.setAddress(update.getAddress());
                    return
                            clinicRepository.save(clinic);
                } else throw new ClinicNotFoundException(id);
            }
        });

    }

    @Override
    public void delete(Long id) {
        transactionTemplate.execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(TransactionStatus status) {
                if (clinicRepository.existsById(id)) {
                    clinicRepository.deleteById(id);
                } else throw new ClinicNotFoundException(id);
                return null;
            }
        });

    }
}
