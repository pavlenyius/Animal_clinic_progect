package com.example.animal_clinic.aop;

import com.example.animal_clinic.AnimalClinicApplication;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LoggingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyConfig.class);


    @Order(10)
    @Before("com.example.animal_clinic.aop.MyPointcuts.methodForAll()")
    public void beforeEachMethod() {
        LOGGER.info("This log before all methods LOG-1");
    }

    @AfterReturning("com.example.animal_clinic.aop.MyPointcuts.getAllClinics(id)")
    public void loginIfSuccessToGetAllClients() {
        LOGGER.info("This log mean success LOG-2");
    }

    @AfterThrowing("com.example.animal_clinic.aop.MyPointcuts.getAllClinics()")
    public void loginIfExceptionToGetAllClients() {
        LOGGER.error("This log mean exception LOG-3");
    }

    @Order(20)
    @Around("com.example.animal_clinic.aop.MyPointcuts.getClientById(id)")
    public Object aroundLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        LOGGER.info("Begin LOG-3");
        var result = proceedingJoinPoint.proceed();
        try {

            LOGGER.info("Success LOG-4 " + proceedingJoinPoint.getSignature().getName());
        } catch (Throwable throwable) {
            LOGGER.error("Exception LOG-5 " + proceedingJoinPoint.getSignature().getName());

        }
        return result;
    }

}
