package dev.t1dmlgus.common.error.exception;

import dev.t1dmlgus.common.error.ErrorType;
import lombok.Getter;


@Getter
public class NotFoundException extends BusinessException{
    public NotFoundException(ErrorType errorType) {
        super(errorType);
    }
}
