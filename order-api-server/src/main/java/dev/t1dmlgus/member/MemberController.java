package dev.t1dmlgus.member;


import dev.t1dmlgus.common.response.CommonResponse;
import dev.t1dmlgus.member.application.MemberCommand;
import dev.t1dmlgus.member.application.MemberInfo;
import dev.t1dmlgus.member.application.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public ResponseEntity<CommonResponse<MemberInfo.MemberToken>> joinMember(@RequestBody MemberDto.JoinMember memberDto) {

        MemberCommand.JoinUser memberCommand = memberDto.toCommand();
        MemberInfo.MemberToken memberToken = memberService.join(memberCommand);
        return ResponseEntity.ok(CommonResponse.of(memberToken, "회원가입이 완료되었습니다."));
    }

}
