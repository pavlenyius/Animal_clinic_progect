package com.example.animal_clinic.repository;

import com.example.animal_clinic.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<ServiceEntity,Long> {
}
