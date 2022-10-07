package de.ktc.booklending.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class AvailableStatusException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 2239259896927305550L;

    public AvailableStatusException(String responseMessage) {
        super(responseMessage);
    }
}
