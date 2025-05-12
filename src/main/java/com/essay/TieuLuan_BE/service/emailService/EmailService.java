package com.essay.TieuLuan_BE.service.emailService;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    GMailer gMailer;
    @KafkaListener(topics = "verificationCodeTopic", groupId = "emailGroup")
    public void listenForVerificationCode(ConsumerRecord<String, String> record) throws Exception {
        String email = record.key();
        String code = record.value();
        sendEmailWithCode(email, code);
    }

    private void sendEmailWithCode(String email, String code) throws Exception {
        String msg= "your verification code is " + code;
        gMailer.sendMail(msg,email);
        System.out.println("Sending code: " + code + " to email: " + email);
    }
}
