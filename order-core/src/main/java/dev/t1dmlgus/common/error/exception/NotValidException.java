package dev.t1dmlgus.common.error.exception;

import dev.t1dmlgus.common.error.ErrorType;
import lombok.Getter;

@Getter
public class NotValidException extends BusinessException{
    public NotValidException(ErrorType errorType) {
        super(errorType);
    }
}
