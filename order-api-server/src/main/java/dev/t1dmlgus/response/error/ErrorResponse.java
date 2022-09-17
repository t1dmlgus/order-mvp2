package dev.t1dmlgus.response.error;

import dev.t1dmlgus.common.error.ErrorType;
import dev.t1dmlgus.response.ResultCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;


@Getter
public class ErrorResponse {

    private final String requestUri;
    private final String errorCode;
    private final String errorMessage;
    private final ResultCode result = ResultCode.FAIL;

    private final List<ErrorField> errorFields;

    @Builder
    private ErrorResponse(String requestUri, String errorCode, String errorMessage, List<ErrorField> errorFields) {
        this.requestUri = requestUri;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorFields = errorFields;
    }

    public static ErrorResponse of(ErrorType errorType, BindingResult bindingResult, HttpServletRequest httpServletRequest){

        return ErrorResponse.builder()
                .requestUri(httpServletRequest.getRequestURI())
                .errorCode(errorType.name())
                .errorMessage(errorType.getMessage())
                .errorFields(ErrorField.of(bindingResult))
                .build();
    }

    public static ErrorResponse of(ErrorType errorType, HttpServletRequest httpServletRequest){

        return ErrorResponse.builder()
                .requestUri(httpServletRequest.getRequestURI())
                .errorCode(errorType.name())
                .errorMessage(errorType.getMessage())
                .build();
    }


    @Getter
    private static class ErrorField {

        private final String field;
        private final String message;

        @Builder
        private ErrorField(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public static ErrorField newInstance(String field, String message) {
            return ErrorField.builder()
                    .field(field)
                    .message(message)
                    .build();
        }


        private static List<ErrorField> of(BindingResult bindingResult) {

            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(i -> ErrorField.newInstance(i.getField(), i.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }
}
