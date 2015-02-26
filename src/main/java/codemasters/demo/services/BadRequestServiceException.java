package codemasters.demo.services;

public class BadRequestServiceException extends RuntimeException {

    public BadRequestServiceException(String message){
        super(message);
    }
}
