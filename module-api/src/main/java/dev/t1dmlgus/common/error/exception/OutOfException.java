package dev.t1dmlgus.common.error.exception;

import dev.t1dmlgus.common.error.type.ErrorType;

public class OutOfException extends BusinessException {
    public OutOfException(ErrorType errorType) {
        super(errorType);
    }
}
