package com.example.animal_clinic.exception;

public class ClinicNotFoundException extends RuntimeException{
    public ClinicNotFoundException (Long id){
        super("Клиника с id " + id +" не найдена");
    }
}
