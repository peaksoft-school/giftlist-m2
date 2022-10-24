package kg.giftlist.giftlistm2.exception;

public class BadCredentialsException extends RuntimeException{

    public BadCredentialsException() {
        super();
    }

    public BadCredentialsException(String message) {
        super(message);
    }

}
