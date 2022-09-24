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


@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입 엔드포인트
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





}
