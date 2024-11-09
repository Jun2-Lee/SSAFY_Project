package com.example.hamin.plan.controller;

import com.example.hamin.plan.domain.requestdto.CreatePlanRequestDto;
import com.example.hamin.plan.domain.requestdto.InviteRequestDto;
import com.example.hamin.plan.domain.requestdto.ParticipatePlanDto;
import com.example.hamin.plan.domain.responsedto.CreatePlanResponseDto;
import com.example.hamin.plan.domain.responsedto.MyPlanResponseDto;
import com.example.hamin.plan.domain.responsedto.PlanChatResponseDto;
import com.example.hamin.plan.domain.responsedto.PlanDetailResponseDto;
import com.example.hamin.plan.model.service.PlanServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/plan")
@Tag(name = "Plan Controller", description = "플랜 관련 API")
public class PlanController {

    private final PlanServiceImpl planService;

    @PostMapping("/create")
    @Operation(summary = "플랜 생성", description = "새로운 플랜을 생성합니다.")
    @ApiResponse(responseCode = "200", description = "플랜 생성 성공", content = @Content(schema = @Schema(implementation = CreatePlanResponseDto.class)))
    public ResponseEntity<?> createPlan(
            @Parameter(description = "플랜 생성 요청 데이터", required = true) @RequestBody CreatePlanRequestDto createPlanDto,
            HttpServletRequest request) {
        String channelId = planService.createPlan(createPlanDto, request.getAttribute("user").toString());
        return ResponseEntity.ok(new CreatePlanResponseDto(channelId));
    }

    @PostMapping("/invite")
    @Operation(summary = "멤버 초대", description = "플랜에 새로운 멤버를 초대합니다.")
    @ApiResponse(responseCode = "200", description = "초대 완료")
    public ResponseEntity<?> inviteMember(
            @Parameter(description = "초대 요청 데이터", required = true) @RequestBody InviteRequestDto inviteRequestDto,
            HttpServletRequest request) {
        planService.inviteMember(inviteRequestDto, request.getAttribute("user").toString());
        return ResponseEntity.ok("초대 완료");
    }

    @PostMapping("/participate")
    @Operation(summary = "플랜 참가", description = "사용자가 지정된 플랜에 참가합니다.")
    @ApiResponse(responseCode = "200", description = "참가 완료", content = @Content(schema = @Schema(example = "채널ID 방에 참가 완료")))
    public ResponseEntity<?> participatePlan(
            @Parameter(description = "플랜 참가 요청 데이터", required = true) @RequestBody ParticipatePlanDto planDto,
            HttpServletRequest request) {
        String channelId = planService.participatePlan(planDto, request.getAttribute("user").toString());
        return ResponseEntity.ok(channelId + "방에 참가 완료");
    }

    @GetMapping("/myplan")
    @Operation(summary = "내 플랜 조회", description = "사용자의 플랜을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "플랜 조회 성공", content = @Content(schema = @Schema(implementation = MyPlanResponseDto.class)))
    public ResponseEntity<?> searchMyPlan(HttpServletRequest request) {
        MyPlanResponseDto myPlanResponseDto = planService.searchMyPlan(request.getAttribute("user").toString());
        return ResponseEntity.ok(myPlanResponseDto);
    }

    @GetMapping("/detail")
    public ResponseEntity<?> searchDetail(@RequestParam Long planId) {
        PlanDetailResponseDto planDetailResponseDto = planService.searchPlanDetail(planId);
        return ResponseEntity.ok(planDetailResponseDto);
    }

    @GetMapping("/chat")
    public ResponseEntity<?> searchChat(@RequestParam Long planId) {
        System.out.println(planId);
        PlanChatResponseDto planChatResponseDto = planService.searchPlanChat(planId);
        return ResponseEntity.ok(planChatResponseDto);
    }
}
