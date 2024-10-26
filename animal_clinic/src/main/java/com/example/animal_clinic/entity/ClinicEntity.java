package com.example.animal_clinic.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class ClinicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Wright the address")
    private  String address;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientEntity clientEntity;


    public ClinicEntity(Long id, String address) {
        this.id = id;
        this.address = address;
    }
}
