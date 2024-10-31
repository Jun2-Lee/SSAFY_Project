package com.example.hamin.session;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ManageSession {
    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();

    // 세션 생성
    public static String createSession(String sessionId, String userId) {
        Session session = new Session(userId);
        sessions.put(sessionId, session);
        return sessionId;
    }

    // 세션 조회
    public static Session getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    // 세션 삭제
    public static void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }

    // 세션 객체
    public static class Session {
        private final String value;
        private final LocalDateTime creationTime;
        public Session(String value) {
            this.value = value;
            this.creationTime = LocalDateTime.now().plusDays(7);
        }

        public String getValue() {
            return value;
        }

        public LocalDateTime getCreationTime() {
            return creationTime;
        }
    }
}
