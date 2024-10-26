package com.example.animal_clinic.service;

import com.example.animal_clinic.exception.ClinicNotFoundException;
import com.example.animal_clinic.exception.ValidationException;
import com.example.animal_clinic.repository.ClinicRepository;
import com.example.animal_clinic.entity.ClinicEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service

public class ClinicService implements BaseService<ClinicEntity, Long> {
    private final ClinicRepository clinicRepository;

    public ClinicService(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
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
        if (clinicEntity.getAddress() == null || clinicEntity.getAddress().isBlank())
        {  throw new ValidationException(" Write the address");}
        return clinicRepository.save(clinicEntity);
    }

    @Override
    public ClinicEntity update(Long id, ClinicEntity update) {
        Optional<ClinicEntity> clinicEntity = clinicRepository.findById(id);
        if (clinicEntity.isPresent()) {
            ClinicEntity clinic = clinicEntity.get();
            clinic.setAddress(update.getAddress());
            return
                    clinicRepository.save(clinic);
        } else throw new ClinicNotFoundException(id);
    }

    @Override
    public void delete(Long id) {
        if (clinicRepository.existsById(id)) {
            clinicRepository.deleteById(id);
        } else throw new ClinicNotFoundException(id);
    }
}
