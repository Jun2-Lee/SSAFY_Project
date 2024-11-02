package com.example.hamin.plan.model.service;

import com.example.hamin.plan.domain.requestdto.CreatePlanRequestDto;
import com.example.hamin.plan.domain.requestdto.InviteRequestDto;
import com.example.hamin.plan.domain.requestdto.ParticipatePlanDto;
import com.example.hamin.plan.domain.responsedto.MyPlanResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface PlanService {
    String createPlan(CreatePlanRequestDto planRequestDto, String email);
    void inviteMember(InviteRequestDto inviteRequestDto, String sender);
    String participatePlan(ParticipatePlanDto planDto, String email);
    MyPlanResponseDto searchMyPlan(String email);
    void changeDetails(String channelId, String data) throws JsonProcessingException;
}
