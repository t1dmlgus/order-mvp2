package dev.t1dmlgus.member.application;

import dev.t1dmlgus.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

public class MemberCommand {

    @Getter
    public static class JoinUser{

        private final String name;
        private final String email;

        @Builder
        public JoinUser(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public static JoinUser newInstance(String name, String email){
            return JoinUser.builder()
                    .name(name)
                    .email(email)
                    .build();
        }

        public Member toMember() {
            return Member.newInstance(name, email);
        }
    }
}
