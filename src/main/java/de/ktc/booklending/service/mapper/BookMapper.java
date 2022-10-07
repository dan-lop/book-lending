package de.ktc.booklending.service.mapper;

import de.ktc.booklending.controller.dto.BookDto;
import de.ktc.booklending.repository.entity.BookEntity;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookEntity dtoToEntity(BookDto dto) {
        return new BookEntity(dto.getBookName(), dto.isAvailable(), dto.getPublicationDate());
    }

    public BookDto entityToDto(BookEntity entity) {
        return new BookDto(entity.getId(), entity.getBookName(), entity.isAvailable(), entity.getPublicationDate());
    }
}
