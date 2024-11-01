package com.example.hamin.relationship;

import com.example.hamin.member.domain.Member;
import com.example.hamin.plan.domain.Plan;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    public MemberPlan(Member member, Plan plan) {
        this.member = member;
        this.plan = plan;
    }
}
