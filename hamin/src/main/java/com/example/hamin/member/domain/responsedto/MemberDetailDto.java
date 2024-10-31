package com.example.hamin.member.domain.responsedto;

import com.example.hamin.member.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class MemberDetailDto {
    private String email;
    private String password;
    private String nickName;
    private String createdAt;

    public MemberDetailDto(Member member) {
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.nickName = member.getNickName();
        this.createdAt = member.getCreatedAt();
    }
}
