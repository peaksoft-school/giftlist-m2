package kg.giftlist.giftlistm2.exception;

public class EmptyLoginException extends RuntimeException{

    public EmptyLoginException() {
        super();
    }

    public EmptyLoginException(String message) {
        super(message);
    }

}
