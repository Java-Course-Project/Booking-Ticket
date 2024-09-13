package org.example.booking.ticket.repository;

import java.util.List;

public interface CrudRepository<E, ID> {
    // Entities returned by Query method should return a defensive/deep copy of entities so that only repository can modify these entities
    E findById(ID id);
    List<E> findAll();
    void save(E e);
    void update(E e);
}
