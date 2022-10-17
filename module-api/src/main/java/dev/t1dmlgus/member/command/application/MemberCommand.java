package dev.t1dmlgus.member.command.application;

import dev.t1dmlgus.member.command.domain.Member;
import lombok.Builder;
import lombok.Getter;

public class MemberCommand {

    @Getter
    public static class JoinUser{

        private final String name;
        private final String email;

        private final String password;

        @Builder
        private JoinUser(String name, String email, String password) {
            this.name = name;
            this.email = email;
            this.password = password;
        }

        public static JoinUser newInstance(String name, String email, String password){
            return JoinUser.builder()
                    .name(name)
                    .email(email)
                    .password(password)
                    .build();
        }

        public Member toMember() {
            return Member.newInstance(name, email, password);
        }
    }

    @Getter
    public static class login {

        private final String email;

        private final String password;

        @Builder
        private login(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public static login newInstance(String email, String password){
            return login.builder()
                    .email(email)
                    .password(password)
                    .build();
        }
    }
}
