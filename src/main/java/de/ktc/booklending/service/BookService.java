package de.ktc.booklending.service;

import de.ktc.booklending.controller.dto.BookDto;
import de.ktc.booklending.controller.dto.BookUpdateDto;
import de.ktc.booklending.repository.BookRepository;
import de.ktc.booklending.repository.entity.BookEntity;
import de.ktc.booklending.service.exception.AvailableStatusException;
import de.ktc.booklending.service.exception.NotFoundException;
import de.ktc.booklending.service.mapper.BookMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public BookDto getById(long id) {
         BookEntity bookEntity = findByIdOrThrowException(id);

        return bookMapper.entityToDto(bookEntity);
    }

    public List<BookDto> getAll(Boolean available) {
        List<BookEntity> bookEntities;
        if (available == null) {
            bookEntities = bookRepository.findAll();
        } else {
            bookEntities = bookRepository.findAllByAvailable(available);
        }

        return bookEntities.stream().map(bookMapper::entityToDto).collect(Collectors.toList());
    }

    public BookDto create(BookDto bookDto) {
        BookEntity bookEntity = bookMapper.dtoToEntity(bookDto);
        BookEntity storedBook = bookRepository.save(bookEntity);

        return bookMapper.entityToDto(storedBook);
    }

    public BookDto update(long id, BookDto bookDto) {
        BookEntity bookToUpdate = findByIdOrThrowException(id);

        bookToUpdate.setBookName(bookDto.getBookName());
        bookToUpdate.setAvailable(bookDto.isAvailable());
        bookToUpdate.setPublicationDate(bookDto.getPublicationDate());

        BookEntity storedBook = bookRepository.save(bookToUpdate);

        return bookMapper.entityToDto(storedBook);
    }

    public BookDto delete(long id) {
        BookEntity bookToDelete = findByIdOrThrowException(id);
        bookRepository.deleteById(id);

        return bookMapper.entityToDto(bookToDelete);
    }

    /**
     * This method changes the available state of the book to enable the lending or returning of a book
     * @param id: Book-ID
     * @param bookUpdateDto: DTO with available state to change
     * @return updated BookDto
     * @throws NotFoundException if the book with the given could not be found
     * @throws AvailableStatusException if the status could not be changed
     * @author Daniel Lopes
     */
    public BookDto updateAvailableStatus(long id, BookUpdateDto bookUpdateDto) {
        BookEntity bookToUpdate = findByIdOrThrowException(id);

        if (bookToUpdate.isAvailable() == bookUpdateDto.isAvailable()) {
            throw new AvailableStatusException("Can't update the available status as the status is already set to '"
                    + bookUpdateDto.isAvailable() + "'.");
        }

        bookToUpdate.setAvailable(bookUpdateDto.isAvailable());
        BookEntity storedBook = bookRepository.save(bookToUpdate);

        return bookMapper.entityToDto(storedBook);
    }

    private BookEntity findByIdOrThrowException(long id) {
        Optional<BookEntity> book = bookRepository.findById(id);

        return book.orElseThrow(() -> new NotFoundException("Unable to find the requested book for the id '" + id + "'."));
    }

}
