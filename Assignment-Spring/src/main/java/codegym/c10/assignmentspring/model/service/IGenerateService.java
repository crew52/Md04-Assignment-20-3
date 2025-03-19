package codegym.c10.assignmentspring.model.service;

import java.util.Optional;

public interface IGenerateService<T> {
    Iterable<T> findAll();

    T save(T T);

    Optional<T> findById(Long id);

    void remove(Long id);
}