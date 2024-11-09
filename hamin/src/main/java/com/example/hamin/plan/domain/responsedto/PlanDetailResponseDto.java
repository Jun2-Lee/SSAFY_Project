package com.example.hamin.plan.domain.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PlanDetailResponseDto {

    private Long planId;
    List<DetailDto> details = new ArrayList<>();

    public PlanDetailResponseDto(Long planId, List<DetailDto> details) {
        this.planId = planId;
        this.details = details;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DetailDto {
        private Long id;
        private String addr;
        private int dayValue;
        private int orderNumber;
        private String name;
        private Double x;
        private Double y;
    }
}
