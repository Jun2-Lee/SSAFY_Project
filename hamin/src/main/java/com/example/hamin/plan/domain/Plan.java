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
    private String channelId;
    private Integer travelDay;
    private String createdAt;
    @OneToMany(mappedBy = "plan")
    private List<MemberPlan> memberList = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "plan_id")
    private List<Detail> details = new ArrayList<>();
}
