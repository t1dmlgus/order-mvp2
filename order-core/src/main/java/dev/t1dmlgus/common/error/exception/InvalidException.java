package dev.t1dmlgus.common.error.exception;

import dev.t1dmlgus.common.error.ErrorType;
import lombok.Getter;

@Getter
public class InvalidException extends BusinessException{
    public InvalidException(ErrorType errorType) {
        super(errorType);
    }
}
