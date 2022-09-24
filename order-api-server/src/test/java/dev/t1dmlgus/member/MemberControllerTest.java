package dev.t1dmlgus.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.t1dmlgus.member.application.MemberCommand;
import dev.t1dmlgus.member.application.MemberInfo;
import dev.t1dmlgus.member.application.MemberService;
import dev.t1dmlgus.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = MemberController.class)
class MemberControllerTest {

    private MockMvc mockMvc;


    @MockBean
    private MemberService memberService;

    @BeforeEach
    void setup(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }


    @Test
    void joinMember() throws Exception {

        //given
        MemberDto.JoinMember memberDto
                = new MemberDto.JoinMember("이의현", "dmlgusgngl@gmail.com","1234");
        Member member = memberDto.toCommand().toMember();
        given(memberService.join(any(MemberCommand.JoinUser.class)))
                .willReturn(MemberInfo.MemberToken.newInstance(member));

        String json = new ObjectMapper().writeValueAsString(memberDto);

        //when
        ResultActions resultActions = mockMvc.perform(
                post("/api/v1/members")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        );


        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("회원가입이 완료되었습니다."))

                // 추후, startsWith와 같은 검증 테스트 필요
                .andExpect(jsonPath("$.data.memberToken").exists())
                .andDo(print()
                );

        verify(memberService).join(any(MemberCommand.JoinUser.class));




    }
}