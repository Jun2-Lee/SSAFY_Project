package com.example.hamin.plan.domain;

import com.example.hamin.detail.domain.Detail;
import com.example.hamin.relationship.MemberPlan;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    public Plan(String title, String channelId, Integer travelDay, String startDate) {
        this.title = title;
        this.channelId = channelId;
        this.travelDay = travelDay;
        this.startDate = startDate;
    }
    private String channelId;
    private Integer travelDay;
    private String startDate;
    private String createdAt;

    public void insertId(Long id) {
        this.id = id;
    }
}
