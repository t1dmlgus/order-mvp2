package dev.t1dmlgus.response.error;


import dev.t1dmlgus.common.error.ErrorType;
import dev.t1dmlgus.common.error.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(value = MethodArgumentNotValidException.class)
//    public ResponseEntity<Object> methodArgumentNotValidationException(MethodArgumentNotValidException e, HttpServletRequest httpServletRequest) {
//
//        ErrorResponse response = ErrorResponse.of(ErrorType.COMMON_INVALID_PARAMETER, e.getBindingResult(), httpServletRequest);
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> entityNotFoundException(BusinessException e, HttpServletRequest httpServletRequest) {

        ErrorResponse response = ErrorResponse.of(e.getErrorType(), httpServletRequest);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(value = DuplicateException.class)
    public ResponseEntity<Object> duplicateException(BusinessException e, HttpServletRequest httpServletRequest) {

        ErrorResponse response = ErrorResponse.of(e.getErrorType(), httpServletRequest);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = NotValidException.class)
    public ResponseEntity<Object> notValidException(BusinessException e, HttpServletRequest httpServletRequest) {

        ErrorResponse response = ErrorResponse.of(e.getErrorType(), httpServletRequest);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = OutOfException.class)
    public ResponseEntity<Object> outOfException(BusinessException e, HttpServletRequest httpServletRequest) {

        ErrorResponse response = ErrorResponse.of(e.getErrorType(), httpServletRequest);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }



    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<Object> serverException(HttpServletRequest httpServletRequest) {

        ErrorResponse response = ErrorResponse.of(ErrorType.INTERNAL_SERVER_ERROR, httpServletRequest);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
