//package com.alekseev.notificationservice;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Component;
//
//@Component//Тестирую SMTP
//public class MailTester implements CommandLineRunner {
//
//    private final JavaMailSender mailSender;
//
//    public MailTester(JavaMailSender mailSender) {
//        this.mailSender = mailSender;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        System.out.println("=== Тестирование подключения к SMTP ===");
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo("stepan-alekseev-87@yandex.ru"); // отправьте сами себе
//            message.setSubject("Тест подключения");
//            message.setText("Если вы видите это письмо, SMTP работает!");
//            mailSender.send(message);
//            System.out.println("✅ Письмо отправлено!");
//        } catch (Exception e) {
//            System.out.println("❌ Ошибка: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//}