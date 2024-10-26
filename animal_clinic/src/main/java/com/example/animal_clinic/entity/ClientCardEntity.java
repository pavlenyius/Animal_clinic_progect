package com.example.animal_clinic.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class ClientCardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String clientInfo;

    @OneToOne
    @JoinColumn(name = "client_id")
    ClientEntity clientEntity;

    public ClientCardEntity(Long id, String clientInfo) {
        this.id = id;
        this.clientInfo = clientInfo;
    }
}
