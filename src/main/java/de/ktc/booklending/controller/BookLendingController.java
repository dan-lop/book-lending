package de.ktc.booklending.controller;

import de.ktc.booklending.controller.dto.BookDto;
import de.ktc.booklending.controller.dto.BookUpdateDto;
import de.ktc.booklending.service.BookService;
import de.ktc.booklending.service.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@Tag(name = "Bucherverleih", description = "Schnittstelle für die Verwaltung der Bücher eines Bücherverleihs")
@RequestMapping(value = "api/books")
public class BookLendingController {

    private final BookService bookService;

    public BookLendingController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Gibt ein einzelnes Buch anhand seiner ID zurück"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Erfolgreiche Anfrage."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Für die übergebene Buch-ID konnte kein Buch gefunden werden.",
                    content = {@Content(schema = @Schema(implementation = NotFoundException.class))}
            ),
    })
    public BookDto getBookById(@PathVariable("id") long id) {
        return bookService.getById(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Gibt alle Bücher entsprechend des Parameters zurück",
            description = "Gibt alle verfügbaren beziehungsweise ausgeliehenen Bücher zurück. " +
                    "Wird kein <b>available</b> parameter angegeben, werden alle Bücher zurückgegeben."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Erfolgreiche Anfrage."
            )
    })
    public List<BookDto> getAll(
            @RequestParam(value = "available", required = false)
            @Parameter(description = "Filtert nach verfügbaren Büchern")
            Boolean available
    ) {
        return bookService.getAll(available);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Fügt ein neues Buch hinzu."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Buch erfolgreich erstellt."
            )
    })
    public BookDto create(@Valid @RequestBody BookDto bookDto) {
        return bookService.create(bookDto);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Bearbeitet ein vorhandenes Buch."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Buch erfolgreich aktualisiert."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Für die übergebene Buch-ID konnte kein Buch gefunden werden.",
                    content = {@Content(schema = @Schema(implementation = NotFoundException.class))}
            ),
    })
    public BookDto update(@PathVariable("id") long id, @Valid @RequestBody BookDto bookDto) {
        return bookService.update(id, bookDto);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Löscht ein vorhandenes Buch."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Buch erfolgreich gelöscht."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Für die übergebene Buch-ID konnte kein Buch gefunden werden.",
                    content = {@Content(schema = @Schema(implementation = NotFoundException.class))}
            ),
    })
    public BookDto delete(@PathVariable("id") long id) {
        return bookService.delete(id);
    }

    @PutMapping(value = "/{id}/available-status", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Leihe ein Buch aus oder gib es zurück.",
            description = "Ermöglicht das Ausleihen beziehungsweise Zurückgeben eines Buches. " +
                    "Setzte dazu <b>\"available\"</b> auf <b>false</b> fürs Ausleihen oder auf <b>true</b> fürs Zurückgeben."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Buch erfolgreich ausgeliehen oder zurückgegeben."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Für die übergebene Buch-ID konnte kein Buch gefunden werden.",
                    content = {@Content(schema = @Schema(implementation = NotFoundException.class))}
            ),
            @ApiResponse(
                    responseCode = "412",
                    description = "Status kann nicht geändert werden. Das Buch ist bereits ausgeliehen oder zurückgegeben.",
                    content = {@Content(schema = @Schema(implementation = NotFoundException.class))}
            ),
    })
    public BookDto updateAvailableStatus(@PathVariable("id") long id, @Valid @RequestBody BookUpdateDto bookUpdateDto) {
        return bookService.updateAvailableStatus(id, bookUpdateDto);
    }

}
