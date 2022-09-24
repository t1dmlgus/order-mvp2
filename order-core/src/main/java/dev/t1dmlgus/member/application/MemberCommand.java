package dev.t1dmlgus.member.application;

import dev.t1dmlgus.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

public class MemberCommand {

    @Getter
    public static class JoinUser{

        private final String name;
        private final String email;

        private final String password;

        @Builder
        public JoinUser(String name, String email, String password) {
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
}
