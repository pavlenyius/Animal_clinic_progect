package com.example.animal_clinic.repository;

import com.example.animal_clinic.entity.ClientCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientCardRepository extends JpaRepository<ClientCardEntity, Long> {
}
