package codemasters.demo.controllers;

import codemasters.demo.model.ErrorMessage;
import codemasters.demo.services.BadRequestServiceException;
import codemasters.demo.services.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BaseController {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorMessage handleResourceNotFoundException(ResourceNotFoundException cause) {
        return new ErrorMessage(cause.getMessage());
    }

    @ExceptionHandler(BadRequestServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorMessage handleBadRequestServiceException(BadRequestServiceException cause) {
        return new ErrorMessage(cause.getMessage());
    }
}
