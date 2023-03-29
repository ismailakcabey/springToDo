package com.example.sprintToDo.demo.config;

import com.example.sprintToDo.demo.service.email.EmailService;
import com.example.sprintToDo.demo.service.fileUpload.ExcelFileUpload;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
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

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("localhost", 6379);
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String,Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

}
