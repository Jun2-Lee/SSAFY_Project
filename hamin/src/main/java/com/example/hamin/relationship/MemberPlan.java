package com.example.hamin.relationship;

import com.example.hamin.member.domain.Member;
import com.example.hamin.plan.domain.Plan;
import jakarta.persistence.*;

@Entity
public class MemberPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;
}
