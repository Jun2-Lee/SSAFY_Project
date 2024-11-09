package com.example.hamin.plan.domain.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PlanChatResponseDto {
    private Long planId;

    private List<ChatDto> chats;

    public PlanChatResponseDto(Long planId, List<ChatDto> chats) {
        this.planId = planId;
        this.chats = chats;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChatDto {
        private Long id;
        private String sender;
        private String content;
        private String createdAt;
    }
}
