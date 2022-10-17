package dev.t1dmlgus.member.application;

import dev.t1dmlgus.member.command.application.MemberCommand;
import dev.t1dmlgus.member.command.application.MemberInfo;
import dev.t1dmlgus.member.command.domain.Member;
import dev.t1dmlgus.member.command.domain.MemberRepository;
import dev.t1dmlgus.member.command.infrastructure.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {


    @InjectMocks
    private MemberServiceImpl memberService;

    @Mock
    private MemberRepository memberRepository;


    @DisplayName("회원가입 후 반환된 memberToken의 시작은 'M'이다.")
    @Test
    void join_user_return_memberToken_start_with_M() {

        //given
        MemberCommand.JoinUser memberCommand = MemberCommand.JoinUser.builder()
                .name("이의현")
                .email("dmlgusgngl@gmail.com")
                .password("1234")
                .build();
        Member member = memberCommand.toMember();
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        //when
        MemberInfo.MemberToken memberToken = memberService.join(memberCommand);

        //then
        Assertions.assertThat(memberToken.getMemberToken().substring(0, 1)).isEqualTo("M");





    }
}