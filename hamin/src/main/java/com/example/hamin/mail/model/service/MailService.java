package com.example.hamin.mail.model.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;
@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;

    public void sendVerificationEmail(String to, String verificationCode) {
        String subject = "이메일 인증입니다.";
        String content = "<p>Please verify your email by clicking the link below:</p>"
                + verificationCode + "\">Verify Now</a>";

        sendEmail(to, subject, content);
    }

    public void sendPlanInvitationEmail(String to, Long planId, String from) {
        String subject = "계획에 참여하세요!";
        String content = "<!DOCTYPE html>\n" +
                "<html lang=\"ko\">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "    <title>여행 계획 초대</title>\n" +
                "  </head>\n" +
                "  <body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0;\">\n" +
                "    <div style=\"max-width: 600px; margin: 20px auto; background-color: #ffffff; border-radius: 8px; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); overflow: hidden;\">\n" +
                "      <!-- Header -->\n" +
                "      <div style=\"background-color: #4caf50; color: white; text-align: center; padding: 20px;\">\n" +
                "        <h1 style=\"margin: 0;\">여행 계획 초대</h1>\n" +
                "      </div>\n" +
                "\n" +
                "      <!-- Content -->\n" +
                "      <div style=\"padding: 20px;\">\n" +
                "        <h2 style=\"color: #333333;\">안녕하세요, 이준희님!</h2>\n" +
                "\n" +
                "        <p style=\"color: #666666; line-height: 1.6;\"><strong>손호민</strong>님이 당신을 여행 계획에 초대했습니다.</p>\n" +
                "\n" +
                "        <p style=\"color: #666666; line-height: 1.6;\">\n" +
                "          아래 버튼을 눌러 여행 계획에 참여해보세요. 새로운 모험이 기다리고\n" +
                "          있습니다!\n" +
                "        </p>\n" +
                "\n" +
                "        <!-- Button -->\n" +
                "        <div style=\"text-align: center; margin-top: 20px;\">\n" +
                "          <a href=\"https://domain.com/invite/planID\" style=\"background-color: #4caf50; color: white; padding: 12px 24px; text-decoration: none; border-radius: 5px; font-size: 16px;\">\n" +
                "            여행 계획에 참여하기\n" +
                "          </a>\n" +
                "        </div>\n" +
                "\n" +
                "        <!-- Additional Info -->\n" +
                "        <p style=\"color: #666666; line-height: 1.6; margin-top: 30px;\">\n" +
                "          여행 계획에 대한 자세한 정보는 언제든지 확인할 수 있습니다. 궁금한 점이 있으면 언제든지 문의하세요.\n" +
                "        </p>\n" +
                "      </div>\n" +
                "\n" +
                "      <!-- Footer -->\n" +
                "      <div style=\"background-color: #f4f4f4; text-align: center; padding: 10px; font-size: 12px; color: #999999;\">\n" +
                "        <p>본 이메일은 xxx님의 요청에 따라 발송되었습니다.</p>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>\n";

        sendEmail(to, subject, content);
    }

    private void sendEmail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new IllegalStateException("메일 전송에 실패하였습니다", e);
        }
    }
}
