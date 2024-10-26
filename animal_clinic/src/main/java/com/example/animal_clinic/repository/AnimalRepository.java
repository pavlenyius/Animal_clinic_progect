package com.example.animal_clinic.repository;


import com.example.animal_clinic.VisitReason;
import com.example.animal_clinic.entity.AnimalEntity;
import com.example.animal_clinic.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<AnimalEntity, Long> {

    @Query("Select a from AnimalEntity a where (:client IS NULL OR a.clientEntity = :client)"+
            "AND (:date IS NULL OR a.visitDate = :date)" +
            "AND (:reason IS NULL OR a.visitReason = :reason)"+
            "AND (:breed IS NULL OR a.animalBreed LIKE %:breed%)")
    List<AnimalEntity> findAnimalByFilter(@Param("client") ClientEntity client,
                                          @Param("date") LocalDate visitDate,
                                          @Param("reason") VisitReason visitReason,
                                          @Param("breed") String animalBreed);
}
