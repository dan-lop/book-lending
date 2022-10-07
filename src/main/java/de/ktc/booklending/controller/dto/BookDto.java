package de.ktc.booklending.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@JsonPropertyOrder({"id", "otherfields"})
public class BookDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -6364925238780975827L;

    private final Long id;

    @NotNull
    @Size(max = 255)
    private final String bookName;

    @NotNull
    private final boolean available;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private final LocalDate publicationDate;

    public BookDto(@JsonProperty("id") Long id,
                   @JsonProperty("bookName") String bookName,
                   @JsonProperty("available") boolean available,
                   @JsonProperty("publicationDate") LocalDate publicationDate) {
        this.id = id;
        this.bookName = bookName;
        this.available = available;
        this.publicationDate = publicationDate;
    }

    public long getId() {
        return id;
    }

    public String getBookName() {
        return bookName;
    }

    public boolean isAvailable() {
        return available;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        BookDto bookDto = (BookDto) o;

        return new EqualsBuilder()
                .append(available, bookDto.available)
                .append(id, bookDto.id)
                .append(bookName, bookDto.bookName)
                .append(publicationDate, bookDto.publicationDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(bookName)
                .append(available)
                .append(publicationDate)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("bookName", bookName)
                .append("available", available)
                .append("publicationDate", publicationDate)
                .toString();
    }
}
