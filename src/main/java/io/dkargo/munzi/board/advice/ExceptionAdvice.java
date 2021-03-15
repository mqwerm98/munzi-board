package io.dkargo.munzi.board.advice;

import io.dkargo.munzi.board.error.DkargoException;
import io.dkargo.munzi.board.error.ErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(DkargoException.class)
    public ResponseEntity<ErrorDTO> dkargoException(DkargoException e) {
        log.debug("### Exception message: {}", e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(new ErrorDTO(e));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = String.format("[%s] %s", e.getBindingResult().getFieldError().getField(), e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        log.error("### Bad request message : {}", errorMessage);
        return ErrorDTO.BAD_REQUEST_ERROR().toResponse();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> illegalArgumentException(IllegalArgumentException e) {
        log.error("### Bad request message : {}", e.getMessage());
        return ErrorDTO.BAD_REQUEST_ERROR().toResponse();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDTO> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("### Bad request message : {}", e.getMessage());
        return ErrorDTO.BAD_REQUEST_ERROR().toResponse();
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ErrorDTO> numberFormatException(NumberFormatException e) {
        log.error("### Bad request message : {}", e.getMessage());
        return ErrorDTO.BAD_REQUEST_ERROR().toResponse();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDTO> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("### Bad request message : {}", e.getMessage());
        return ErrorDTO.BAD_REQUEST_ERROR().toResponse();
    }

}
