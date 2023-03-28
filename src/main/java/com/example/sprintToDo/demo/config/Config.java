package com.example.sprintToDo.demo.config;

import com.example.sprintToDo.demo.service.email.EmailService;
import com.example.sprintToDo.demo.service.fileUpload.ExcelFileUpload;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public EmailService emailService() {
        return new EmailService();
    }

    @Bean
    public ExcelFileUpload excelFileUpload(){
        EmailService emailService = new EmailService();
        return new ExcelFileUpload(emailService);
    }

}
