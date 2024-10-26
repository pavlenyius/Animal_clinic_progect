package com.example.animal_clinic.repository;

import com.example.animal_clinic.entity.ClinicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicRepository extends JpaRepository<ClinicEntity, Long> {
}
