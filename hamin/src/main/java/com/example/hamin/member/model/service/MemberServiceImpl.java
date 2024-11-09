package com.example.hamin.member.model.service;

import com.example.hamin.mail.model.service.MailService;
import com.example.hamin.member.domain.Member;
import com.example.hamin.member.domain.requestdto.LoginDto;
import com.example.hamin.member.domain.requestdto.SignUpDto;
import com.example.hamin.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberMapper memberMapper;
    private final MailService mailService;
    @Override
    public void signUp(SignUpDto signUpDto) {
        Member member = new Member(signUpDto.getEmail(), signUpDto.getPassword(), signUpDto.getNickName());
        memberMapper.signUp(member);
    }

    @Override
    public Member signIn(LoginDto loginDto) {
        return memberMapper.findByEmailPassword(loginDto.getEmail(), loginDto.getPassword());
    }



    @Override
    public void updateNickName(String email, String nickName) {
        memberMapper.updateNickName(email, nickName);
    }

    @Override
    public Member searchByEmail(String email) {
        return memberMapper.findByEmail(email);
    }

    @Override
    public void sendEmail(String email) {
        System.out.println(email);
        mailService.sendVerificationEmail(email.trim(), createCode());
    }

    @Override
    public boolean searchByNickName(String nickName) {
        return memberMapper.findByNickName(nickName) == null;
    }


    private String createCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }
}
