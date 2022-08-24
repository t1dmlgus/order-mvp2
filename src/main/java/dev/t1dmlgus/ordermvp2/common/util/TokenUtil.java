package dev.t1dmlgus.ordermvp2.common.util;

import java.util.UUID;

public class TokenUtil {

    private static final int TOKEN_LENGTH = 13;

    public static String generateToken(String type) {

        String prefix = "";
        if (type.equals("order")) {
            prefix = "A";
        } else if (type.equals("member")) {
            prefix = "M";
        }else if (type.equals("product")) {
            prefix = "P";
        }else {
            throw new RuntimeException("토큰을 생성할 수 없습니다(토큰타입예외)");
        }

        return createToken(prefix);
    }

    private static String createToken(String prefix) {
        String randomToken =
                prefix + UUID.randomUUID().toString().replace("-", "");
        return randomToken.substring(0, TOKEN_LENGTH);
    }
}
