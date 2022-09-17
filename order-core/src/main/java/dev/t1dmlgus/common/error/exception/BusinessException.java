package dev.t1dmlgus.common.error.exception;

import dev.t1dmlgus.common.error.ErrorType;
import lombok.Getter;


@Getter
public class BusinessException extends RuntimeException{

    private final ErrorType errorType;

    public BusinessException(ErrorType errorType) {
        this.errorType = errorType;
    }
}
