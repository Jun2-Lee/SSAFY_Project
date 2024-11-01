package com.example.hamin.plan.domain.requestdto;

import lombok.Getter;

@Getter
public class CreatePlanRequestDto {
    /*
    "duration" : 3,
	"title" : "abc",
	"startDate" : "2024-10-30"
     */
    private int duration;
    private String title;
    private String startDate;

}
