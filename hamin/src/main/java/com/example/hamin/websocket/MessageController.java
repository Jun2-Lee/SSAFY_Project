package com.example.hamin.websocket;

import com.example.hamin.plan.model.service.PlanServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
@Tag(name = "Message Controller", description = "WebSocket 메시지 처리 API")
public class MessageController {

    private final PlanServiceImpl planService;
    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @MessageMapping("/hello")
    @Operation(summary = "메시지 전송", description = "채널 ID에 따라 메시지를 WebSocket을 통해 전송합니다.")
    public void message(Message message) {
        simpMessageSendingOperations.convertAndSend("/sub/channel/" + message.getChannelId(), message);
        try {
            String jsonData = message.getData();
            String type = message.getType();
            if("plan".equals(type)) {
                planService.changeDetails(message.getChannelId(), message.getData());
            } else if("message".equals(type)) {
                // 다른 메시지 처리 로직
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
