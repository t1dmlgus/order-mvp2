package dev.t1dmlgus.common.error.exception;

import dev.t1dmlgus.common.error.ErrorType;

public class AuthorizedException extends BusinessException{

    public AuthorizedException(ErrorType errorType) {
        super(errorType);
    }
}
