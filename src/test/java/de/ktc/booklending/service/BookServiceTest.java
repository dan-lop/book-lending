package de.ktc.booklending.service;

import de.ktc.booklending.controller.dto.BookDto;
import de.ktc.booklending.controller.dto.BookUpdateDto;
import de.ktc.booklending.repository.BookRepository;
import de.ktc.booklending.repository.entity.BookEntity;
import de.ktc.booklending.service.exception.AvailableStatusException;
import de.ktc.booklending.service.exception.NotFoundException;
import de.ktc.booklending.service.mapper.BookMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("it")
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService serviceUnderTest;


    @Test
    void ShouldThrowExceptionIfThereIsNoBookWithGivenId() {
        // given
        long id = 1;

        // when
        when(bookRepository.findById(id))
                .thenReturn(Optional.empty());

        // then
        assertThrows(NotFoundException.class, () -> serviceUnderTest.getById(id));
    }

    @Test
    void ShouldThrowExceptionWhenUpdatingAvailableStateIfStateIsTheSame() {
        // given
        long id = 1;
        BookEntity entity = new BookEntity();
        entity.setBookName("Der Herr der Ringe");
        entity.setAvailable(true);

        BookUpdateDto bookUpdateDto = new BookUpdateDto(true);

        // when
        when(bookRepository.findById(id))
                .thenReturn(Optional.of(entity));

        // then
        assertThrows(AvailableStatusException.class, () -> serviceUnderTest.updateAvailableStatus(id, bookUpdateDto));
    }

    @Test
    void ShouldNotThrowExceptionIfAvailableStateIsDifferent() {
        // given
        long id = 1;
        BookEntity entity = new BookEntity("Der Herr der Ringe", true, null);

        BookUpdateDto bookUpdateDto = new BookUpdateDto(false);

        // when
        when(bookRepository.findById(id))
                .thenReturn(Optional.of(entity));

        // then
        assertDoesNotThrow( () -> serviceUnderTest.updateAvailableStatus(id, bookUpdateDto));
    }

    @Test
    void ShouldUpdateBookIfABookWithGivenIdExists() {
        // given
        long id = 1;
        BookDto bookDto = new BookDto(id, "Der Herr der Ringe", true, null);
        BookEntity entity = new BookEntity();

        // when
        when(bookRepository.findById(id))
                .thenReturn(Optional.of(entity));
        when(bookRepository.save(entity))
                .thenReturn(entity);

        // then
        assertDoesNotThrow(() -> serviceUnderTest.update(id, bookDto));
    }
}
