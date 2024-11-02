package com.example.hamin.websocket;

import com.example.hamin.plan.model.service.PlanService;
import com.example.hamin.plan.model.service.PlanServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final PlanServiceImpl planService;
    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /*
        /sub/channel/12345      - 구독(channelId:12345)
        /pub/hello              - 메시지 발행
    */
    @MessageMapping("/hello")
    public void message(Message message) {

        simpMessageSendingOperations.convertAndSend("/sub/channel/" + message.getChannelId(), message);
        try {
            String jsonData = message.getData();
            String type = message.getType();
            if("plan".equals(type)) {
                planService.changeDetails(message.getChannelId(), message.getData());
            }
            else if("message".equals(type)) {
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
