package dev.t1dmlgus.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;



@Getter
@RequiredArgsConstructor
public enum ErrorType {

    COMMON_INVALID_PARAMETER( "요청한 값이 올바르지 않습니다."),
    INVALID_PARAMETER_MEMBER_TOKEN( "주문자는 필수 입니다."),
    INVALID_PARAMETER_DELIVERY( "배송정보는 필수 입니다."),
    INVALID_CHANGE_DELIVERY( "배송정보를 변경할 수 없습니다."),


    NOT_FOUND_PRODUCT( "해당 상품을 찾을 수 없습니다."),
    NOT_FOUND_MEMBER( "회원을 찾을 수 없습니다."),
    NOT_FOUND_ORDER( "해당 주문을 찾을 수 없습니다."),

    OUT_OF_STOCK( "해당 상품의 재고가 부족합니다."),

    DUPLICATED_PRODUCT( "이미 존재하는 상품입니다."),
    GENERATE_TOKEN_ERROR( "토큰을 생성할 수 없습니다(토큰타입예외)"),

    INTERNAL_SERVER_ERROR("서버 내부 에러");

    private final String message;
}
