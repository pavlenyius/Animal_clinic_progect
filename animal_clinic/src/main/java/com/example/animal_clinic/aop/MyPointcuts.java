package com.example.animal_clinic.aop;

import org.aspectj.lang.annotation.Pointcut;

public class MyPointcuts {

    @Pointcut("within(com.example.animal_clinic.service.ClientServise+)")
    public void methodForAll(){}

    @Pointcut("execution (List<ClientEntity> com.example.animal_clinic.service.ClientServise.getAll())")
    public void getAllClinics(){}

    @Pointcut("execution (ClientEntity com.example.animal_clinic.service.ClientServise.get*(id)")
    public void getClientById(Long id){}


}
