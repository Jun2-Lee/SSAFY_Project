package com.example.hamin.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /*
        /sub/channel/12345      - 구독(channelId:12345)
        /pub/hello              - 메시지 발행
    */
    @MessageMapping("/hello")
    public void message(Message message) {
        try {
            String jsonData = message.getData();
            Map<String, Object> dataMap = objectMapper.readValue(jsonData, Map.class);
            System.out.println("Parsed JSON data: " + dataMap.get("content"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        simpMessageSendingOperations.convertAndSend("/sub/channel/" + message.getChannelId(), message);
    }
}
