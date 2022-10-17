package dev.t1dmlgus.member.command.application;

import dev.t1dmlgus.member.command.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberInfo {

    @Getter
    public static class MemberToken {

        private final String memberToken;

        @Builder
        private MemberToken(String memberToken) {
            this.memberToken = memberToken;
        }

        public static MemberToken newInstance(Member member){
            return MemberToken.builder()
                    .memberToken(member.getMemberToken())
                    .build();
        }

    }

    @Getter
    public static class MemberDetail{

        private final String memberToken;
        private final String name;
        private final String email;


        @Builder
        private MemberDetail(String memberToken, String name, String email) {
            this.memberToken = memberToken;
            this.name = name;
            this.email = email;
        }

        public static MemberDetail newInstance(Member member){
            return MemberDetail.builder()
                    .memberToken(member.getMemberToken())
                    .name(member.getName())
                    .email(member.getEmail())
                    .build();
        }
    }
}