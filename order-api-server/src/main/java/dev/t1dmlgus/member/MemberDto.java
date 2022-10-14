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

        @NotBlank(message = "비밀번호를 입력해주세요")
        private String password;

        public JoinMember(String name, String email, String password) {
            this.name = name;
            this.email = email;
            this.password = password;
        }

        public MemberCommand.JoinUser toCommand(){
            return MemberCommand.JoinUser.newInstance(name, email, password);
        }
    }


    @NoArgsConstructor
    @ToString
    @Getter
    public static class login{

        @NotBlank(message = "이메일을 입력해주세요")
        private String email;

        @NotBlank(message = "비밀번호를 입력해주세요")
        private String password;

        public login(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public MemberCommand.login toCommand(){
            return MemberCommand.login.newInstance(email, password);
        }
    }
}
