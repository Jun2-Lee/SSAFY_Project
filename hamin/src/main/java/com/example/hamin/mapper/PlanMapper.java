package com.example.hamin.mapper;

import com.example.hamin.chat.domain.Chat;
import com.example.hamin.detail.domain.Detail;
import com.example.hamin.member.domain.Member;
import com.example.hamin.plan.domain.Plan;
import com.example.hamin.plan.domain.responsedto.MyPlanResponseDto;
import com.example.hamin.plan.domain.responsedto.PlanChatResponseDto;
import com.example.hamin.plan.domain.responsedto.PlanDetailResponseDto;
import com.example.hamin.relationship.MemberPlan;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PlanMapper {

    @Select("select * from member where email = #{email}")
    @Results({
            @Result(property = "nickName", column = "nick_name"),
            @Result(property = "createdAt", column = "created_at")
    })
    Member findByEmail(String email);

    @Select("SELECT * FROM plan WHERE id = #{id}")
    @Results({
            @Result(property = "channelId", column = "channel_id"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "travelDay", column = "travel_day"),
            @Result(property = "startDate", column = "start_date")
    })
    Plan findPlanById(@Param("id") Long id);

    @Select("SELECT id FROM plan WHERE channel_id = #{channelId}")
    Long findPlanByChannelId(@Param("channelId") String channelId);

    @Select("""
            SELECT d.*
            FROM detail d
            WHERE d.plan_id = #{planId}
            """)
    @Results({
            @Result(property = "dayValue", column = "day_value"),
            @Result(property = "orderNumber", column = "order_number")
    })
    List<PlanDetailResponseDto.DetailDto> searchPlanDetail(@Param("planId") Long planId);

    @Select("""
            SELECT c.*
            FROM chat c
            WHERE c.plan_id = #{planId}
            ORDER BY c.created_at ASC
            """)
    @Results({
            @Result(property = "createdAt", column = "created_at"),
    })
    List<PlanChatResponseDto.ChatDto> searchPlanChat(@Param("planId") Long planId);

    @Select("""
            SELECT p.*,
                   (SELECT COUNT(*)
                    FROM member_plan mp2
                    WHERE mp2.plan_id = p.id) AS participant
            FROM plan p
            JOIN member_plan mp ON p.id = mp.plan_id
            WHERE mp.member_id = #{memberId}
            GROUP BY p.id;
            """)
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "channelId", column = "channel_id"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "travelDay", column = "travel_day"),
            @Result(property = "participant", column = "participant"),
            @Result(property = "startDate", column = "start_date")
    })
    List<MyPlanResponseDto.PlanResponseDto> searchMyPlan(@Param("memberId") Long memberId);

    @Insert("""
            INSERT INTO detail (addr, day_value, order_number, name, x, y, plan_id)
            VALUES (#{detail.addr}, #{detail.dayValue}, #{detail.orderNumber}, #{detail.name}, #{detail.x}, #{detail.y}, #{planId})
            """)
    void insertDetail(@Param("detail") Detail detail, @Param("planId") Long planId);

    @Insert("""
            INSERT INTO chat (sender, content, created_at, plan_id)
            VALUES (#{chat.sender}, #{chat.content}, NOW(), #{planId})
            """)
    void insertChat(@Param("chat") Chat chat, @Param("planId") Long planId);

    @Delete("""
            DELETE FROM DETAIL WHERE plan_id = #{planId}
            """)
    void deleteDetail(@Param("planId") Long planId);

    @Insert("INSERT INTO member_plan (member_id, plan_id) VALUES (#{member.id}, #{plan.id})")
    void insertMemberPlan(MemberPlan memberPlan);

    @Insert("INSERT INTO plan (channel_id, title, travel_day, start_date, created_at) " +
            "VALUES (#{channelId}, #{title}, #{travelDay}, #{startDate}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertPlan(Plan plan);
}
