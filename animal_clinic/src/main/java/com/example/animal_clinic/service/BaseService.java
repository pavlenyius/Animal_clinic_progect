package com.example.animal_clinic.service;

import java.util.List;

public interface BaseService<T, ID> {

    List<T> getAll();

    T getById(ID id);

    T addNew(T entity);

    T update(ID id, T entity);

    void delete(ID id);

}
