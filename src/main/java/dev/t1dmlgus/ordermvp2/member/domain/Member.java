package dev.t1dmlgus.ordermvp2.member.domain;


import dev.t1dmlgus.ordermvp2.AbstractEntity;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    public Member(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
