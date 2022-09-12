package dev.t1dmlgus.member.domain;


import dev.t1dmlgus.common.util.AbstractEntity;
import dev.t1dmlgus.common.util.TokenUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "members")
public class Member extends AbstractEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String memberToken;
    private String name;
    private String email;

    @Builder
    private Member(String name, String email) {
        this.name = name;
        this.email = email;
        generateMemberToken();
    }

    public static Member newInstance(String name, String email){
        return Member.builder()
                .name(name)
                .email(email)
                .build();
    }

    public void generateMemberToken(){
        this.memberToken = TokenUtil.generateToken("member");
    }
}
