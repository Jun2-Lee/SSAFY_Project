package com.example.hamin.member.domain.responsedto;

import lombok.Getter;

@Getter
public class CheckLoginDto {
    private boolean isLogin;
    private String nickName;
    private String email;
    private String profile;

    public CheckLoginDto(boolean isLogin, String nickName, String email, String profile) {
        this.isLogin = isLogin;
        this.nickName = nickName;
        this.email = email;
        this.profile = profile;
    }
}
