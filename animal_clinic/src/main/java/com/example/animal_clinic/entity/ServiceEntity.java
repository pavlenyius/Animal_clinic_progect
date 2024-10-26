package com.example.animal_clinic.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    @NotBlank
    private String typeOfSrevice;

    @ManyToMany
    @JoinTable(name = "client_service",
            joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "client_id"))
    private List<ClientEntity> clientEntityList;

    public ServiceEntity(Long id, String typeOfSrevice) {
        this.id = id;
        this.typeOfSrevice = typeOfSrevice;
    }
}
