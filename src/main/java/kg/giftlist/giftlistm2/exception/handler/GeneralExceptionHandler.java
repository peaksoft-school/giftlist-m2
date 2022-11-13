package kg.giftlist.giftlistm2.exception.handler;

import kg.giftlist.giftlistm2.exception.*;
import kg.giftlist.giftlistm2.exception.ExceptionResponse.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(EmptyLoginException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse notFoundHandle(EmptyLoginException e) {
        return new ExceptionResponse(HttpStatus.NOT_FOUND, e.getClass().getName(), e.getMessage());
    }

    @ExceptionHandler(IncorrectLoginException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse incorrectLoginHandle(IncorrectLoginException e) {
        return new ExceptionResponse(HttpStatus.FORBIDDEN, e.getClass().getName(), e.getMessage());
    }

    @ExceptionHandler(EmptyValueException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse emptyValueHandle(EmptyValueException e) {
        return new ExceptionResponse(HttpStatus.NOT_FOUND, e.getClass().getName(), e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse badCredentialHandle(BadCredentialsException e) {
        return new ExceptionResponse(HttpStatus.FORBIDDEN, e.getClass().getName(), e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse userNotFoundException(UserNotFoundException userNotFoundException) {
        return new ExceptionResponse(HttpStatus.NOT_FOUND, userNotFoundException.getClass().getName(),
                userNotFoundException.getMessage());
    }

    @ExceptionHandler(UserExistException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse userAllReadyExist(UserExistException userExistException) {
        return new ExceptionResponse(HttpStatus.FORBIDDEN, userExistException.getClass().getName(),
                userExistException.getMessage());
    }

    @ExceptionHandler(WishListExistException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse wishListAllReadyExist(WishListExistException wishListExistException) {
        return new ExceptionResponse(HttpStatus.FORBIDDEN, wishListExistException.getClass().getName(),
                wishListExistException.getMessage());
    }

    @ExceptionHandler(NotificationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse notificationNotFoundException(NotificationNotFoundException notificationNotFoundException) {
        return new ExceptionResponse(HttpStatus.NOT_FOUND, notificationNotFoundException.getClass().getName(),
                notificationNotFoundException.getMessage());
    }

    @ExceptionHandler(BookingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse bookingNotFoundException(BookingNotFoundException bookingNotFoundException) {
        return new ExceptionResponse(HttpStatus.NOT_FOUND, bookingNotFoundException.getClass().getName(),
                bookingNotFoundException.getMessage());
    }

}