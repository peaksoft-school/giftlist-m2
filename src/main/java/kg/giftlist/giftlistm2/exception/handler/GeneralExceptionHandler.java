package kg.giftlist.giftlistm2.exception.handler;

import kg.giftlist.giftlistm2.exception.IncorrectLoginException;
import kg.giftlist.giftlistm2.exception.ExceptionResponse.ExceptionResponse;
import kg.giftlist.giftlistm2.exception.EmptyLoginException;
import kg.giftlist.giftlistm2.exception.MyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(EmptyLoginException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse NotFoundHandle(EmptyLoginException e) {
        return new ExceptionResponse(HttpStatus.NOT_FOUND, e.getClass().getName(), e.getMessage());
    }

    @ExceptionHandler(IncorrectLoginException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse BadCredentialsHandle(IncorrectLoginException e) {
        return new ExceptionResponse(HttpStatus.FORBIDDEN, e.getClass().getName(), e.getMessage());
    }

    @ExceptionHandler(MyException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse exceptionResponse(MyException myException){
        return new ExceptionResponse(HttpStatus.FORBIDDEN, myException.getClass().getName()
                ,myException.getMessage());
    }

}