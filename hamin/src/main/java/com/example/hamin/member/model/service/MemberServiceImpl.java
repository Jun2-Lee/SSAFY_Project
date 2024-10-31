package com.example.hamin.member.model.service;

import com.example.hamin.member.domain.Member;
import com.example.hamin.member.domain.requestdto.LoginDto;
import com.example.hamin.member.domain.requestdto.SignUpDto;
import com.example.hamin.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberMapper memberMapper;
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
}
