package dev.t1dmlgus.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommonResponse<T> {

    private final String message;
    private final T data;
    private final ResultCode result = ResultCode.SUCCESS;

    @Builder
    private CommonResponse(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public static <T> CommonResponse<T> of(T data, String message){
        return CommonResponse.<T>builder()
                .data(data)
                .message(message)
                .build();
    }

    public static <T> CommonResponse<T> of(String message){
        return CommonResponse.<T>builder()
                .message(message)
                .build();
    }
}


