package com.example.hamin.chat.domain;

import com.example.hamin.plan.domain.Plan;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sender;
    private String content;
    private String createdAt;

    public Chat(String sender, String content) {
        this.sender = sender;
        this.content = content;
    }

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;
}
