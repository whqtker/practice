package org.ll.apiresponse.handler;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.ll.apiresponse.dto.ApiResponse;
import org.ll.apiresponse.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse error = ErrorResponse.of(
                "BAD_REQUEST",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(error));
    }
}
