package de.ktc.booklending.controller;


import de.ktc.booklending.repository.BookRepository;
import de.ktc.booklending.repository.entity.BookEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("it")
class BookLendingControllerTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnBookById() throws Exception {
        BookEntity bookEntity = new BookEntity("Der Herr der Ringe", true, LocalDate.of(1954, 10, 10));
        bookEntity = bookRepository.save(bookEntity);

        mockMvc.perform(get("/api/books/"+bookEntity.getId()).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(bookEntity.getBookName())));
    }

    @Test
    void shouldReturnAllBooksIfNoAvailableStatusIsGiven() throws Exception {
        BookEntity bookEntity1 = new BookEntity("Der Herr der Ringe", true, LocalDate.of(1954, 10, 10));
        BookEntity bookEntity2 = new BookEntity("Der Hobbit", false, LocalDate.of(1937, 11, 11));
        bookEntity1 = bookRepository.save(bookEntity1);
        bookEntity2 = bookRepository.save(bookEntity2);

        mockMvc.perform(get("/api/books/").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(bookEntity1.getBookName())))
                .andExpect(content().string(containsString(bookEntity2.getBookName())));
    }

    @Test
    void shouldReturnAllAvailableBooksIfParameterAvailableIsTrue() throws Exception {
        BookEntity bookEntity1 = new BookEntity("Der Herr der Ringe", true, LocalDate.of(1954, 10, 10));
        BookEntity bookEntity2 = new BookEntity("Der Hobbit", false, LocalDate.of(1937, 11, 11));
        bookEntity1 = bookRepository.save(bookEntity1);
        bookEntity2 = bookRepository.save(bookEntity2);

        mockMvc.perform(get("/api/books/").contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("available", "true"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(bookEntity1.getBookName())))
                .andExpect(content().string(not(containsString(bookEntity2.getBookName()))));
    }
}
