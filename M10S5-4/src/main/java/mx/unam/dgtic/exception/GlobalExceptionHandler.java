package mx.unam.dgtic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice //para decir que es una excepcion global
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
   public ResponseEntity<ErrorDetails> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
     ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(), ex.getMessage());
     return ResponseEntity
             .status(HttpStatus.NOT_FOUND)
             .contentType(MediaType.APPLICATION_JSON)
             .body(errorDetails);
   }

    static class ErrorDetails {
        private int status;
        private String message;

        public ErrorDetails(int status, String message) {
            this.status = status;
            this.message = message;
        }

        // Getters
        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

        // Setters omitidos por brevedad
    }
}
