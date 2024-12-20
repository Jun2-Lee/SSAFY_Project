package com.example.hamin.member.domain;

import com.example.hamin.relationship.MemberPlan;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String profile;
    private String email;
    private String password;
    private String nickName;
    private String createdAt;

    public Member(String email, String password, String nickName, String profile) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.profile = profile;
    }

    public void changeNickName(String nickName) {
        this.nickName = nickName;
    }

}
