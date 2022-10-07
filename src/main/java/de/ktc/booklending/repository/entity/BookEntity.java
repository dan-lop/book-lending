package de.ktc.booklending.repository.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Entity
@Table(name = "book")
@SequenceGenerator(name = "seq_book", sequenceName = "SEQ_BOOK", allocationSize = 1)
public class BookEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 8106933603856252528L;
    @Id
    @GeneratedValue(generator = "SEQ_BOOK")
    private Long id;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "last_modified_on")
    private ZonedDateTime lastModifiedOn;

    @NotNull
    @Size(max = 255)
    @Column(name = "book_name")
    private String bookName;

    @Column(name = "available")
    private boolean available;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    public BookEntity(
            String bookName,
            boolean available,
            LocalDate publicationDate) {
        this.bookName = bookName;
        this.available = available;
        this.publicationDate = publicationDate;
    }

    public BookEntity() { }

    @PrePersist
    private void prePersist() {
        this.createdOn = ZonedDateTime.now();
        this.lastModifiedOn = ZonedDateTime.now();
    }

    @PreUpdate
    private void preUpdate() {
        this.lastModifiedOn = ZonedDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        BookEntity that = (BookEntity) o;

        return new EqualsBuilder()
                .append(available, that.available)
                .append(id, that.id)
                .append(createdOn, that.createdOn)
                .append(lastModifiedOn, that.lastModifiedOn)
                .append(bookName, that.bookName)
                .append(publicationDate, that.publicationDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(createdOn)
                .append(lastModifiedOn)
                .append(bookName)
                .append(available)
                .append(publicationDate)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("createdOn", createdOn)
                .append("lastModifiedOn", lastModifiedOn)
                .append("bookName", bookName)
                .append("available", available)
                .append("publicationDate", publicationDate)
                .toString();
    }
}
