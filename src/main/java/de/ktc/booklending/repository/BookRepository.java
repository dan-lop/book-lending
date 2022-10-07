package de.ktc.booklending.repository;

import de.ktc.booklending.repository.entity.BookEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<BookEntity, Long> {

    List<BookEntity> findAll();
    List<BookEntity> findAllByAvailable(boolean available);
}
