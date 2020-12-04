package com.example.rabbitmq.rabbitmqdemo.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jacksparrow414
 * @date 2020/12/4 10:15
 */
@Configuration
public class BasicConfig {

    @Bean
    public Queue basicQueue() {
        return new Queue("basic");
    }
}
