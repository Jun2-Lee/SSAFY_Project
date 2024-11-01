package com.example.hamin.member.domain.responsedto;

import lombok.Getter;

@Getter
public class CheckLoginDto {
    private boolean isLogin;
    private String nickName;
    private String email;

    public CheckLoginDto(boolean isLogin, String nickName, String email) {
        this.isLogin = isLogin;
        this.nickName = nickName;
        this.email = email;
    }
}
