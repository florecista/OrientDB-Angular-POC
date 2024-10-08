package socialnetwork.view;

import socialnetwork.exceptions.NonExistingPersonException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Maps exceptions to HTTP codes
 * @author moises.macero
 */
@RestControllerAdvice
public class PersonExceptionHandler {

    @ExceptionHandler(NonExistingPersonException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNonExistingHero() {
    }
}
