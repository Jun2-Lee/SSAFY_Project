package com.example.hamin.plan.controller;

import com.example.hamin.plan.domain.requestdto.CreatePlanRequestDto;
import com.example.hamin.plan.domain.requestdto.InviteRequestDto;
import com.example.hamin.plan.domain.requestdto.ParticipatePlanDto;
import com.example.hamin.plan.domain.responsedto.CreatePlanResponseDto;
import com.example.hamin.plan.domain.responsedto.MyPlanResponseDto;
import com.example.hamin.plan.model.service.PlanServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/plan")
public class PlanController {

    private final PlanServiceImpl planService;
    @PostMapping("/create")
    public ResponseEntity<?> createPlan(@RequestBody CreatePlanRequestDto createPlanDto, HttpServletRequest request) {
        String channelId = planService.createPlan(createPlanDto, request.getAttribute("user").toString());
        return ResponseEntity.ok(new CreatePlanResponseDto(channelId));
    }

    @PostMapping("/invite")
    public ResponseEntity<?> inviteMember(@RequestBody InviteRequestDto inviteRequestDto, HttpServletRequest request) {
        planService.inviteMember(inviteRequestDto, request.getAttribute("user").toString());
        return ResponseEntity.ok("초대 완료");
    }

    @PostMapping("/participate")
    public ResponseEntity<?> participatePlan(@RequestBody ParticipatePlanDto planDto, HttpServletRequest request) {
        String channelId = planService.participatePlan(planDto, request.getAttribute("user").toString());
        return ResponseEntity.ok(channelId + "방에 참가 완료");
    }

    @GetMapping("/myplan")
    public ResponseEntity<?> searchMyPlan(HttpServletRequest request) {
        MyPlanResponseDto myPlanResponseDto = planService.searchMyPlan(request.getAttribute("user").toString());
        return ResponseEntity.ok(myPlanResponseDto);
    }
}
