package com.example.hamin.chat.domain;

import com.example.hamin.plan.domain.Plan;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sender;
    private String content;
    private String createdAt;
}
