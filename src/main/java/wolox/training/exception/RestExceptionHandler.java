package wolox.training.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Class for exception control
 *
 * @author luismiguelrodriguez
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({BookNotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(Exception e, WebRequest webRequest) {
        return handleExceptionInternal(e, "Book not found", new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }

    @ExceptionHandler({BookAlreadyOwnedException.class})
    protected ResponseEntity<Object> handleAlreadyOwned(Exception e, WebRequest webRequest) {
        return handleExceptionInternal(e, "book is already registered ", new HttpHeaders(), HttpStatus.CONFLICT, webRequest);
    }

    @ExceptionHandler({UsersNotFoundException.class})
    protected ResponseEntity<Object> handleUserNotFound(Exception e, WebRequest webRequest) {
        return handleExceptionInternal(e, "User not found", new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }

}
