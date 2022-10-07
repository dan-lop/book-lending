package de.ktc.booklending.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

public class BookUpdateDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -7746255367522351791L;

    @NotNull
    private final boolean available;

    public BookUpdateDto(@JsonProperty("available") boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        BookUpdateDto that = (BookUpdateDto) o;

        return new EqualsBuilder()
                .append(available, that.available)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(available)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("available", available)
                .toString();
    }
}
