package com.example.hamin.mapper;

import com.example.hamin.member.domain.Member;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MemberMapper {

    @Select("select * from member where email = #{email} and password = #{password}")
    @Results({
            @Result(property = "nickName", column = "nick_name"),
            @Result(property = "createdAt", column = "created_at")
    })
    Member findByEmailPassword(String email, String password);

    @Select("select * from member where email = #{email}")
    @Results({
            @Result(property = "nickName", column = "nick_name"),
            @Result(property = "createdAt", column = "created_at")
    })
    Member findByEmail(String email);

    @Update("update member set nick_name = #{nickName} where email = #{email}")
    void updateNickName(String email, String nickName);

    @Insert("insert into member (email, nick_name, password, created_at) " +
            "values (#{email}, #{nickName}, #{password}, NOW())")
    void signUp(Member member);
}