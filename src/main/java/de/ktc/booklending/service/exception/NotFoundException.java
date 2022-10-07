package de.ktc.booklending.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 6207742760011362602L;

    public NotFoundException(String responseMessage) {
        super(responseMessage);
    }
}
