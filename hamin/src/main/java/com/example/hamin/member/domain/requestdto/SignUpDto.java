package com.example.hamin.member.domain.requestdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDto {
    private String email;
    private String nickName;
    private String password;

}
