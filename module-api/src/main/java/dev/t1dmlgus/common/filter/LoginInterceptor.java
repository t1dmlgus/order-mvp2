package dev.t1dmlgus.common.filter;

import dev.t1dmlgus.common.error.exception.AuthorizedException;
import dev.t1dmlgus.common.error.type.ErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * 로그인 체크 인터셉터
 *
 * @author t1dmlgus
 * @version 1.0
 */

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 세션 처리(Interceptor)
     *
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler chosen handler to execute, for type and/or instance evaluation
     * @return true 핸들러 호출
     * @throws AuthorizedException 미인증 예외
     */

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        log.info("미인증 요청 url: {}", requestURI);

        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("LOGIN_MEMBER") == null) {
            throw new AuthorizedException(ErrorType.UNAUTHORIZED);
        }
        return true;
    }
}



