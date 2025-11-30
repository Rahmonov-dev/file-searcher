package org.architect.filereader.exceptions;

import org.architect.filereader.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneral(Exception ex) {
        return ResponseEntity
                .status(500)
                .body(new ErrorResponse("INTERNAL_ERROR", ex.getMessage()));
    }

    @ExceptionHandler(FileReadException.class)
    public ResponseEntity<ErrorResponse> handleFileReadException(FileReadException ex) {
        return ResponseEntity
                .status(500)
                .body(new ErrorResponse(ex.getMessage(), "FileReadException"));
    }

}
