package kg.giftlist.giftlistm2.Exception.Handler;

import kg.giftlist.giftlistm2.Exception.IncorrectUsersLoginException;
import kg.giftlist.giftlistm2.validation.ValidationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class IncorrectUsersLoginExceptionHandler extends ResponseEntityExceptionHandler {

    @Setter
    @Getter
    @AllArgsConstructor
    private static class CorrectException{
        private String message;
    }

    @ExceptionHandler(IncorrectUsersLoginException.class)
    protected ResponseEntity<CorrectException> handle() {
        return new ResponseEntity<>(new CorrectException(ValidationType.LOGIN_FAILED), HttpStatus.NOT_FOUND);
    }

}
