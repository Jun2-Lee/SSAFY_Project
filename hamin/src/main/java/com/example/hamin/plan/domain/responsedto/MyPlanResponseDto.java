package com.example.hamin.plan.domain.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MyPlanResponseDto {
    List<PlanResponseDto> myPlans = new ArrayList<>();

    public MyPlanResponseDto(List<PlanResponseDto> myPlans) {
        this.myPlans = myPlans;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PlanResponseDto {
        private Long id;
        private String title;
        private int participant;
        private int travelDay;
        private String channelId;
        private String startDate;
        private String createdAt;
    }
}
