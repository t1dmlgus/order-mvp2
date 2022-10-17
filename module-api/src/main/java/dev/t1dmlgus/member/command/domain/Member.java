package dev.t1dmlgus.member.command.domain;


import dev.t1dmlgus.common.AbstractEntity;
import dev.t1dmlgus.common.util.token.Token;
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

    private String password;

    @Builder
    private Member(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        generateMemberToken();
    }

    public static Member newInstance(String name, String email, String password){
        return Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
    }

    public void generateMemberToken(){
        this.memberToken = Token.generateToken("member");
    }
}
