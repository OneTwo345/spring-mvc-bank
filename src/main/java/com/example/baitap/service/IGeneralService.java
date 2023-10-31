package com.example.baitap.service;

import java.util.List;
import java.util.Optional;

public interface IGeneralService<E, T> {

    List<E> findAll(boolean deleted);

    Optional<E> findById(T id);

    void create(E e);

    void update(T id, E e);

    void removeById(T id);
}
