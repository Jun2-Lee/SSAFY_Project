package com.example.hamin.plan.model.service;

import com.example.hamin.detail.domain.Detail;
import com.example.hamin.mail.model.service.MailService;
import com.example.hamin.mapper.PlanMapper;
import com.example.hamin.member.domain.Member;
import com.example.hamin.plan.domain.Plan;
import com.example.hamin.plan.domain.requestdto.CreatePlanRequestDto;
import com.example.hamin.plan.domain.requestdto.InviteRequestDto;
import com.example.hamin.plan.domain.requestdto.ParticipatePlanDto;
import com.example.hamin.plan.domain.responsedto.MyPlanResponseDto;
import com.example.hamin.relationship.MemberPlan;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class PlanServiceImpl implements PlanService{

    private final MailService mailService;
    private final ObjectMapper objectMapper;

    private final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final PlanMapper planMapper;
    @Override
    public String createPlan(CreatePlanRequestDto requestDto, String email) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        String channelId = sb.toString();
        Plan plan = new Plan(requestDto.getTitle(), channelId, requestDto.getDuration(), requestDto.getStartDate());
        planMapper.insertPlan(plan);
        Member member = planMapper.findByEmail(email);
        MemberPlan memberPlan = new MemberPlan(member, plan);
        planMapper.insertMemberPlan(memberPlan);
        return channelId;
    }

    @Override
    public void inviteMember(InviteRequestDto inviteRequestDto, String sender) {
        System.out.println(inviteRequestDto.getReceiver());
        mailService.sendPlanInvitationEmail(inviteRequestDto.getReceiver(), inviteRequestDto.getPlanId(), sender);
    }

    @Override
    public String participatePlan(ParticipatePlanDto planDto, String email) {
        Plan plan = planMapper.findPlanById(planDto.getPlanId());
        Member member = planMapper.findByEmail(email);
        MemberPlan memberPlan = new MemberPlan(member, plan);
        planMapper.insertMemberPlan(memberPlan);
        return plan.getChannelId();
    }

    @Override
    @Transactional(readOnly = true)
    public MyPlanResponseDto searchMyPlan(String email) {
        Long memberId = planMapper.findByEmail(email).getId();
        MyPlanResponseDto planResponseDto = new MyPlanResponseDto(planMapper.searchMyPlan(memberId));
        return planResponseDto;
    }

    @Override
    public void changeDetails(String channelId, String data) throws JsonProcessingException {
        List<Detail> details = objectMapper.readValue(data, new TypeReference<List<Detail>>() {});
        Long planId = planMapper.findPlanByChannelId(channelId);
        planMapper.deleteDetail(planId);
        for (Detail detail : details) {
            System.out.println(detail.getDayValue());
            planMapper.insertDetail(detail, planId);
        }
    }
}
