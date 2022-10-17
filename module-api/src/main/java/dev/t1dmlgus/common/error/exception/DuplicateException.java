package dev.t1dmlgus.common.error.exception;

import dev.t1dmlgus.common.error.type.ErrorType;

public class DuplicateException extends BusinessException {
    public DuplicateException(ErrorType errorType) {
        super(errorType);
    }

}
