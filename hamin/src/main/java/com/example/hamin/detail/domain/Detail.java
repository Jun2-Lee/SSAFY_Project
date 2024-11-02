package com.example.hamin.detail.domain;

import com.example.hamin.plan.domain.Plan;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String addr;
    private int dayValue;
    private int orderNumber;
    private String name;
    private Double x;
    private Double y;
    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

}
