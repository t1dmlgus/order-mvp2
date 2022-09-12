package dev.t1dmlgus.member;

import dev.t1dmlgus.member.application.MemberCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

public class MemberDto {

    @NoArgsConstructor
    @ToString
    @Getter
    public static class JoinMember{

        @NotBlank(message = "이름을 입력해주세요")
        private String name;

        @NotBlank(message = "이메일을 입력해주세요")
        private String email;

        public JoinMember(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public MemberCommand.JoinUser toCommand(){
            return MemberCommand.JoinUser.newInstance(name, email);
        }
    }
}
