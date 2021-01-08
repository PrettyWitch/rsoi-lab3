package com.yuhan.service.warranty;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * @author yuhan
 * @date 13.11.2020 - 15:14
 * @purpose
 */
@SpringBootApplication
@EnableTransactionManagement
public class WarrantyApplication {
    public static void main(String[] args) {
        SpringApplication.run(WarrantyApplication.class, args);
    }

    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

}
