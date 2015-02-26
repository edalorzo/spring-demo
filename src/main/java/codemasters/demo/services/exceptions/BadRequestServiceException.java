package codemasters.demo.services.exceptions;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.Arrays;
import java.util.Formatter;

public class BadRequestServiceException extends RuntimeException {

    public BadRequestServiceException(String message) {
        super(message);
    }

    public BadRequestServiceException(Errors errors){
        super(getMessage(errors));
    }

    private static String getMessage(Errors errors){
        Formatter out = new Formatter();
        for(ObjectError error : errors.getFieldErrors()){
            if(error instanceof FieldError){
                FieldError fieldError = (FieldError) error;
                out.format("%s.%s %s but it is %s%n", fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage(), fieldError.getRejectedValue());
            } else {
                out.format("%s %s [%s]%n", error.getObjectName(), error.getDefaultMessage(), Arrays.toString(error.getArguments()));
            }
        }
        return out.toString();
    }


}
