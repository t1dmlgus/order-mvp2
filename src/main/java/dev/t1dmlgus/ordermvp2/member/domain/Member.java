package dev.t1dmlgus.ordermvp2.member.domain;


import dev.t1dmlgus.ordermvp2.AbstractEntity;

import javax.persistence.*;

@Entity
@Table(name = "members")
public class Member extends AbstractEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    public Member(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
