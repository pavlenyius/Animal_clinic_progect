package com.example.animal_clinic.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "You shout wright the name")
    private String name;

    @Email
    private String email;

    @Pattern(regexp = "\\d{11}", message = "Phone number must be exactly 11 digits")
    private String phoneNumber;

    @OneToOne(mappedBy = "clientEntity", cascade = CascadeType.ALL)
    ClientCardEntity clientCardEntity;


    @OneToMany(mappedBy = "clientEntity",cascade = CascadeType.ALL)
    List<ClinicEntity> clinicEntities;

    @OneToMany(mappedBy = "clientEntity")
   List <AnimalEntity> animalEntities;

    @ManyToMany(mappedBy = "clientEntityList")
  List<ServiceEntity> serviceEntities;

    public ClientEntity(Long id, String name, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
