package com.example.hamin.interceptor;

import com.example.hamin.session.ManageSession;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        String sessionId = null;
        if (cookies != null) {
            sessionId = Arrays.stream(cookies)
                    .filter(cookie -> "JSESSIONID".equals(cookie.getName()))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
        }
        if (sessionId != null) {
            ManageSession.Session session = ManageSession.getSession(sessionId);
            if(session != null) {
                if(session.getCreationTime().isBefore(LocalDateTime.now())) {
                    System.out.println("세션 아이디가 만료됨");
                    ManageSession.removeSession(sessionId);
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("로그인 상태가 아닙니다");
                    return false;
                }
                request.setAttribute("user", session.getValue());
            }
            else {
                System.out.println("세션 아이디가 틀렸음");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("로그인 상태가 아닙니다");
                return false;
            }
        }
        else {
            System.out.println("세션 아이디 없음");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("로그인 상태가 아닙니다");
            return false;
        }
        System.out.println("인터셉터 테스트");
        return true;
    }
}
