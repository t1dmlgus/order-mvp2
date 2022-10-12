package dev.t1dmlgus.member;


import dev.t1dmlgus.member.application.MemberCommand;
import dev.t1dmlgus.member.application.MemberInfo;
import dev.t1dmlgus.member.application.MemberService;
import dev.t1dmlgus.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@RequiredArgsConstructor
@RestController
public class MemberController {

    private static final String SESSION_KEY = "LOGIN_MEMBER";

    private final MemberService memberService;

    /**
     * 회원가입
     *
     * @param joinDto dto to join
     * @return memberToken, resultCode, message
     */
    @PostMapping("/api/v1/members")
    public ResponseEntity<CommonResponse<MemberInfo.MemberToken>> joinMember(@RequestBody MemberDto.JoinMember joinDto) {

        MemberCommand.JoinUser memberCommand = joinDto.toCommand();
        MemberInfo.MemberToken memberToken = memberService.join(memberCommand);
        return ResponseEntity.ok(CommonResponse.of(memberToken, "회원가입이 완료되었습니다."));
    }

    /**
     * 로그인
     *
     * @param loginDto dto to login
     * @return memberToken, resultCode, message
     */
    @PostMapping("/api/v1/login")
    public ResponseEntity<CommonResponse<MemberInfo.MemberToken>> login(@RequestBody MemberDto.login loginDto, HttpServletRequest request) {

        MemberCommand.login memberCommand = loginDto.toCommand();
        MemberInfo.MemberToken memberToken = memberService.login(memberCommand);

        // 세션 생성
        createSession(request, memberToken);
        return ResponseEntity.ok(CommonResponse.of(memberToken, "로그인이 완료되었습니다."));
    }

    /**
     * 세션생성
     *
     * @param request HttpServletRequest
     * @param memberToken MemberToken
     */
    private static void createSession(HttpServletRequest request, MemberInfo.MemberToken memberToken) {
        HttpSession session = request.getSession();
        session.setAttribute(SESSION_KEY, memberToken.getMemberToken());
    }


    /**
     * 로그아웃
     *
     * @param request HttpServletRequest
     * @return 로그아웃 메시지
     */
    @PostMapping("/api/v1/logout")
    public ResponseEntity<CommonResponse<String>> logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok(CommonResponse.of( "로그아웃 되었습니다."));
    }

}