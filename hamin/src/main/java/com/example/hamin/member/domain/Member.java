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
    private String email;
    private String password;
    private String nickName;
    private String createdAt;
    @OneToMany(mappedBy = "member")
    private List<MemberPlan> planList = new ArrayList<>();

    public Member(String email, String password, String nickName) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
    }

    public void changeNickName(String nickName) {
        this.nickName = nickName;
    }

}
