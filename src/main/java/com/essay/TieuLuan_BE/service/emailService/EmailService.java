package com.essay.TieuLuan_BE.service.emailService;

import com.essay.TieuLuan_BE.entity.User;
import com.essay.TieuLuan_BE.repository.UserRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    GMailer gMailer;
    @KafkaListener(topics = "verificationCodeTopic", groupId = "emailGroup")
    public void listenForVerificationCode(ConsumerRecord<String, String> record) throws Exception {
//        System.out.println("da nhan dc message");
        String email = record.key();
        String code = record.value();
        sendEmailWithCode(email, code);
    }
    @KafkaListener(topics = "newPasswordTopic", groupId = "emailGroup")
    public void listenForNewPassword(ConsumerRecord<String, String> record) throws Exception {
        String email = record.key();
        String password = record.value();
        sendEmailWithNewPassword(email, password);
    }
    private void sendEmailWithNewPassword(String email, String password) throws Exception {
        String msg= "your new password is: " + password;
        gMailer.sendMail(msg,email);
        User user=userRepository.findByEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
    private void sendEmailWithCode(String email, String code) throws Exception {
//        System.out.println("da gui message");
        String msg= "your verification code is " + code;
        gMailer.sendMail(msg,email);
    }
}
