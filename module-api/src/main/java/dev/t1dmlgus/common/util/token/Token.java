package dev.t1dmlgus.common.util.token;

import dev.t1dmlgus.common.error.exception.BusinessException;
import dev.t1dmlgus.common.error.type.ErrorType;

import java.util.UUID;

public class Token {

    private static final int TOKEN_LENGTH = 13;

    public static String generateToken(String type) {

        String prefix = "";
        if (type.equals("order")) {
            prefix = "R";
        } else if (type.equals("member")) {
            prefix = "M";
        }else if (type.equals("product")) {
            prefix = "P";
        }else {
            throw new BusinessException(ErrorType.GENERATE_TOKEN_ERROR);
        }

        return createToken(prefix);
    }

    private static String createToken(String prefix) {
        String randomToken =
                prefix + UUID.randomUUID().toString().replace("-", "");
        return randomToken.substring(0, TOKEN_LENGTH);
    }
}
